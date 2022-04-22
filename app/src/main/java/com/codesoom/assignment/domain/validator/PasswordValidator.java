package com.codesoom.assignment.domain.validator;

import com.codesoom.assignment.common.validateRuleInfo.PassWordRuleInfo;
import org.springframework.util.Assert;


public class PasswordValidator implements Validator {
    private final String password;

    public PasswordValidator(String password) {
        this.password = password;
    }

    private void checkPasswordEmpty(String password) {
        String errorMessage = PassWordRuleInfo.EMPTY_PASSWORD.getValidateMessage();
        Assert.hasText(password, errorMessage);
    }

    private void checkContinuousNumber(String password) {
        int allowContinuousNumber = (int) PassWordRuleInfo.ALLOW_CONTINUOUS_NUMBER.getRuleInfo();
        String validateMessage = PassWordRuleInfo.ALLOW_CONTINUOUS_NUMBER.getValidateMessage();
        boolean isOverFlowContinuousNumber = isOverFlowContinuousNumber(password, allowContinuousNumber);
        StringBuilder stringBuilder = new StringBuilder();
        String errorText = stringBuilder.append(allowContinuousNumber)
                                        .append(validateMessage)
                                        .toString();

        Assert.isTrue(!isOverFlowContinuousNumber,errorText);
     }

    private int initSaveContinuousCount() {
        return 0;
    }

    private boolean isOverFlowContinuousNumber(String password, int allowContinuousNumber) {
        int stringSize = password.length();
        int saveContinuousCount = initSaveContinuousCount();

        for (int offset = 0; offset < stringSize; offset++) {
            int charAsc = password.charAt(offset);

            if (charAsc >= 48 && charAsc <= 57) {
                if (allowContinuousNumber == saveContinuousCount) {
                    return true;
                }

                if (offset != 0) {
                    int beforeAsc = password.charAt(Math.decrementExact(offset));
                    int diffBeforeAfter = Math.subtractExact(charAsc, beforeAsc);

                    if (diffBeforeAfter == 1) {
                        saveContinuousCount = Math.incrementExact(saveContinuousCount);
                    } else {
                        saveContinuousCount = initSaveContinuousCount();
                    }
                }
            } else {
                saveContinuousCount = initSaveContinuousCount();
            }
        }
        return false;
    }

    @Override
    public boolean isSatisfiedBy() {
        checkPasswordEmpty(password);
        checkContinuousNumber(password);
        return true;
    }
}
