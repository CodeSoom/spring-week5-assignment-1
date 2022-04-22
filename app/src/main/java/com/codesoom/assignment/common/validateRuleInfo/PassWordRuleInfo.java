package com.codesoom.assignment.common.validateRuleInfo;

public enum PassWordRuleInfo {
    ALLOW_CONTINUOUS_NUMBER(3,"회 이상 연속된 문자열을 사용할 수 없습니다"),
    EMPTY_PASSWORD(null,"비밀번호를 입력해주세요");

    private final Object ruleInfo;
    private final String validateMessage;
    PassWordRuleInfo(Object ruleInfo,String validateMessage){
        this.ruleInfo= ruleInfo;
        this.validateMessage=validateMessage;
    }

    public Object getRuleInfo() {
        return ruleInfo;
    }

    public String getValidateMessage() {
        return validateMessage;
    }
}
