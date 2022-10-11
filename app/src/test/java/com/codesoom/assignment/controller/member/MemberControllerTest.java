package com.codesoom.assignment.controller.member;

import com.codesoom.assignment.application.member.MemberCommand;
import com.codesoom.assignment.application.member.MemberCommand.UpdateRequest;
import com.codesoom.assignment.application.member.MemberCommandService;
import com.codesoom.assignment.application.member.MemberInfo;
import com.codesoom.assignment.common.MemberSampleFactory;
import com.codesoom.assignment.common.exception.InvalidParamException;
import com.codesoom.assignment.common.exception.MemberNotFoundException;
import com.codesoom.assignment.controller.member.MemberDto.RequestParam;
import com.codesoom.assignment.controller.member.MemberDto.UpdateParam;
import com.codesoom.assignment.domain.member.Member;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("MemberCommandController 클래스")
class MemberControllerTest {
    @Autowired
    private WebApplicationContext ctx;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    MemberCommandService memberCommandService;

    @BeforeEach
    void setup() {
        // ResponseBody JSON에 한글이 깨지는 문제로 추가
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @Nested
    @DisplayName("registerMember[/users::POST] 메소드는")
    class Describe_registerMember {
        ResultActions subject(RequestParam request) throws Exception {
            return mockMvc.perform(post("/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)));
        }

        @Nested
        @DisplayName("새로운 회원정보가 주어지면")
        class Context_with_new_member {
            private final RequestParam givenRequest = MemberSampleFactory.createRequestParam();

            @BeforeEach
            void prepare() {
                Member member = Member.builder()
                        .id(1L)
                        .name(givenRequest.getName())
                        .password(givenRequest.getPassword())
                        .email(givenRequest.getEmail())
                        .build();
                given(memberCommandService.createMember(any(MemberCommand.Register.class)))
                        .willReturn(new MemberInfo(member));
            }

            @Test
            @DisplayName("CREATED(201)와 등록된 회원정보를 리턴한다")
            void it_returns_201_registered_member() throws Exception {
                final ResultActions resultActions = subject(givenRequest);

                resultActions.andExpect(status().isCreated())
                        .andExpect(jsonPath("name").value(equalTo(givenRequest.getName())))
                        .andExpect(jsonPath("password").value(equalTo(givenRequest.getPassword())))
                        .andDo(print());
            }
        }

        @Nested
        @DisplayName("필수항목에 빈 값이 주어지면")
        class Context_with_blank_field {
            private final List<RequestParam> testList = new ArrayList<>();

            @BeforeEach
            void prepare() {
                testList.add(MemberSampleFactory.createRequestParamWith(MemberSampleFactory.FieldName.NAME, MemberSampleFactory.ValueType.NULL));
                testList.add(MemberSampleFactory.createRequestParamWith(MemberSampleFactory.FieldName.NAME, MemberSampleFactory.ValueType.EMPTY));
                testList.add(MemberSampleFactory.createRequestParamWith(MemberSampleFactory.FieldName.NAME, MemberSampleFactory.ValueType.BLANK));
                testList.add(MemberSampleFactory.createRequestParamWith(MemberSampleFactory.FieldName.PASSWORD, MemberSampleFactory.ValueType.NULL));
                testList.add(MemberSampleFactory.createRequestParamWith(MemberSampleFactory.FieldName.PASSWORD, MemberSampleFactory.ValueType.EMPTY));
                testList.add(MemberSampleFactory.createRequestParamWith(MemberSampleFactory.FieldName.PASSWORD, MemberSampleFactory.ValueType.BLANK));
                testList.add(MemberSampleFactory.createRequestParamWith(MemberSampleFactory.FieldName.EMAIL, MemberSampleFactory.ValueType.NULL));
                testList.add(MemberSampleFactory.createRequestParamWith(MemberSampleFactory.FieldName.EMAIL, MemberSampleFactory.ValueType.EMPTY));
                testList.add(MemberSampleFactory.createRequestParamWith(MemberSampleFactory.FieldName.EMAIL, MemberSampleFactory.ValueType.BLANK));
            }

            @Test
            @DisplayName("BAD_REQUEST(400)와 에러메시지를 리턴한다")
            void it_returns_400_and_error_message() {
                testList.forEach(this::test);
            }

            private void test(RequestParam request) {
                try {
                    ResultActions resultActions = subject(request);

                    resultActions.andExpect(status().isBadRequest())
                            .andDo(print());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }

        @Nested
        @DisplayName("유효하지않은 이메일이 주어지면")
        class Context_with_invalid_email {
            private final RequestParam givenRequest = new RequestParam();

            @BeforeEach
            void prepare() {
                givenRequest.setName("홍길동");
                givenRequest.setPassword("test1234");
            }

            @Test
            @DisplayName("BAD_REQUEST(400)와 에러메시지를 리턴한다")
            void it_returns_400_and_error_message() throws Exception {
                ResultActions resultActions = subject(givenRequest);

                resultActions.andExpect(status().isBadRequest())
                        .andDo(print());
            }
        }

        @Nested
        @DisplayName("InvalidParamException이 발생하면")
        class Context_with_illegal_argument_exception {
            @BeforeEach
            void prepare() {
                given(memberCommandService.createMember(any(MemberCommand.Register.class)))
                        .willThrow(new InvalidParamException("입력값이 비어있습니다."));
            }
            @Test
            @DisplayName("BAD_REQUEST(400)과 에러 메시지를 리턴한다")
            void it_returns_400_and_error_message() throws Exception {
                ResultActions resultActions = subject(MemberSampleFactory.createRequestParam());

                resultActions.andExpect(status().isBadRequest())
                        .andDo(print());
            }
        }
    }

    @Nested
    @DisplayName("updateMember[/users/id::PATCH] 메소드는")
    class Describe_updateMember {
        ResultActions subject(Long id, MemberDto.UpdateParam request) throws Exception {
            return mockMvc.perform(patch("/users/{id}", id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)));
        }

        @Nested
        @DisplayName("유효한 ID가 주어지면")
        class Context_with_valid_id {
            private final Long MEMBER_ID = 1L;
            private final Member savedMember = MemberSampleFactory.createMember(MEMBER_ID);
            private final UpdateParam givenRequest = new UpdateParam();

            @BeforeEach
            void prepare() {
                givenRequest.setName("수정_" + savedMember.getName());
                givenRequest.setPassword("수정_" + savedMember.getPassword());

                given(memberCommandService.updateMember(any(UpdateRequest.class)))
                        .willReturn(new MemberInfo(MemberFactory.of(MEMBER_ID, givenRequest).toEntity()));
            }

            @Test
            @DisplayName("회원정보를 수정하고 OK(200)와 수정된 상품을 리턴한다")
            void it_returns_200_and_modified_member() throws Exception {
                final ResultActions resultActions = subject(MEMBER_ID, givenRequest);

                resultActions.andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("name").value(equalTo(givenRequest.getName())))
                        .andExpect(jsonPath("password").value(equalTo(givenRequest.getPassword())))
                        .andDo(print());
            }
        }

        @Nested
        @DisplayName("유효하지않은 ID가 주어지면")
        class Context_with_invalid_id {
            private final Long MEMBER_ID = 9999L;
            private final MemberDto.UpdateParam givenRequest = MemberSampleFactory.createUpdateParam();

            @BeforeEach
            void prepare() {
                given(memberCommandService.updateMember(any(MemberCommand.UpdateRequest.class))).willThrow(new MemberNotFoundException(MEMBER_ID));
            }

            @Test
            @DisplayName("NOT_FOUND(404)와 예외 메시지를 리턴한다")
            void it_returns_404_and_message() throws Exception {
                ResultActions resultActions = subject(MEMBER_ID, givenRequest);

                resultActions.andExpect(status().isNotFound())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("message", containsString("등록되지 않은 회원입니다.")))
                        .andDo(print());
            }
        }

        @Nested
        @DisplayName("필수항목에 빈 값이 주어지면")
        class Context_with_blank_name {
            private final Long MEMBER_ID = 1L;
            private final List<UpdateParam> testList = new ArrayList<>();

            @BeforeEach
            void prepare() {
                testList.add(MemberSampleFactory.createUpdateParamWith(MemberSampleFactory.FieldName.NAME, MemberSampleFactory.ValueType.NULL));
                testList.add(MemberSampleFactory.createUpdateParamWith(MemberSampleFactory.FieldName.NAME, MemberSampleFactory.ValueType.EMPTY));
                testList.add(MemberSampleFactory.createUpdateParamWith(MemberSampleFactory.FieldName.NAME, MemberSampleFactory.ValueType.BLANK));
                testList.add(MemberSampleFactory.createUpdateParamWith(MemberSampleFactory.FieldName.PASSWORD, MemberSampleFactory.ValueType.NULL));
                testList.add(MemberSampleFactory.createUpdateParamWith(MemberSampleFactory.FieldName.PASSWORD, MemberSampleFactory.ValueType.EMPTY));
                testList.add(MemberSampleFactory.createUpdateParamWith(MemberSampleFactory.FieldName.PASSWORD, MemberSampleFactory.ValueType.BLANK));
            }

            @Test
            @DisplayName("BAD_REQUEST(400)와 에러메시지를 리턴한다")
            void it_returns_400_and_error_message() {
                testList.forEach(this::test);
            }

            private void test(UpdateParam request) {
                try {
                    ResultActions resultActions = subject(MEMBER_ID, request);

                    resultActions.andExpect(status().isBadRequest())
                            .andDo(print());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Nested
    @DisplayName("deleteMember[/users/id::DELETE] 메소드는")
    class Describe_deleteMember {
        ResultActions subject(Long id) throws Exception {
            return mockMvc.perform(delete("/users/{id}", id)
                    .contentType(MediaType.APPLICATION_JSON));
        }

        @Nested
        @DisplayName("유효한 ID가 주어지면")
        class Context_with_valid_id {
            private final Long MEMBER_ID = 1L;

            @BeforeEach
            void prepare() {
                doNothing().when(memberCommandService).deleteMember(MEMBER_ID);
            }

            @Test
            @DisplayName("회원정보를 삭제하고 NO_CONTENT(204)를 리턴한다")
            void it_returns_204() throws Exception {
                final ResultActions resultActions = subject(MEMBER_ID);

                resultActions.andExpect(status().isNoContent())
                        .andDo(print());
            }
        }

        @Nested
        @DisplayName("유효하지않은 ID가 주어지면")
        class Context_with_invalid_id {
            private final Long MEMBER_ID = 100L;
            @BeforeEach
            void prepare() {
                doThrow(new MemberNotFoundException(MEMBER_ID)).when(memberCommandService).deleteMember(MEMBER_ID);
            }

            @Test
            @DisplayName("NOT_FOUND(404)와 예외 메시지를 리턴한다")
            void it_returns_404_and_message() throws Exception {
                final ResultActions resultActions = subject(MEMBER_ID);

                resultActions.andExpect(status().isNotFound())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("message", containsString("등록되지 않은 회원입니다.")))
                        .andDo(print());
            }
        }
    }
}
