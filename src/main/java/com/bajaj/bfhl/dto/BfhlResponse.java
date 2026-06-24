package com.bajaj.bfhl.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;


// Response DTO for POST /bfhl.

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(builder = BfhlResponse.Builder.class)
public final class BfhlResponse {

    @JsonProperty("is_success")
    private final boolean isSuccess;

    @JsonProperty("user_id")
    private final String userId;

    @JsonProperty("email")
    private final String email;

    @JsonProperty("roll_number")
    private final String rollNumber;

    @JsonProperty("odd_numbers")
    private final List<String> oddNumbers;

    @JsonProperty("even_numbers")
    private final List<String> evenNumbers;

    @JsonProperty("alphabets")
    private final List<String> alphabets;

    @JsonProperty("special_characters")
    private final List<String> specialCharacters;

    @JsonProperty("sum")
    private final String sum;

    @JsonProperty("concat_string")
    private final String concatString;

    private BfhlResponse(Builder builder) {
        this.isSuccess         = builder.isSuccess;
        this.userId            = builder.userId;
        this.email             = builder.email;
        this.rollNumber        = builder.rollNumber;
        this.oddNumbers        = builder.oddNumbers;
        this.evenNumbers       = builder.evenNumbers;
        this.alphabets         = builder.alphabets;
        this.specialCharacters = builder.specialCharacters;
        this.sum               = builder.sum;
        this.concatString      = builder.concatString;
    }

    //Getters
    @JsonProperty("is_success")
    public boolean isSuccess()                     { return isSuccess; }
    public String getUserId()                      { return userId; }
    public String getEmail()                       { return email; }
    public String getRollNumber()                  { return rollNumber; }
    public List<String> getOddNumbers()            { return oddNumbers; }
    public List<String> getEvenNumbers()           { return evenNumbers; }
    public List<String> getAlphabets()             { return alphabets; }
    public List<String> getSpecialCharacters()     { return specialCharacters; }
    public String getSum()                         { return sum; }
    public String getConcatString()                { return concatString; }

    //Factory

    public static Builder builder() { return new Builder(); }

    //Builder 
    public static final class Builder {

        @JsonProperty("is_success")
        private boolean isSuccess;

        @JsonProperty("user_id")
        private String userId;

        @JsonProperty("email")
        private String email;

        @JsonProperty("roll_number")
        private String rollNumber;

        @JsonProperty("odd_numbers")
        private List<String> oddNumbers;

        @JsonProperty("even_numbers")
        private List<String> evenNumbers;

        @JsonProperty("alphabets")
        private List<String> alphabets;

        @JsonProperty("special_characters")
        private List<String> specialCharacters;

        @JsonProperty("sum")
        private String sum;

        @JsonProperty("concat_string")
        private String concatString;

        public Builder isSuccess(boolean val)            { isSuccess = val;          return this; }
        public Builder userId(String val)                { userId = val;             return this; }
        public Builder email(String val)                 { email = val;              return this; }
        public Builder rollNumber(String val)            { rollNumber = val;         return this; }
        public Builder oddNumbers(List<String> val)      { oddNumbers = val;         return this; }
        public Builder evenNumbers(List<String> val)     { evenNumbers = val;        return this; }
        public Builder alphabets(List<String> val)       { alphabets = val;          return this; }
        public Builder specialCharacters(List<String> v) { specialCharacters = v;    return this; }
        public Builder sum(String val)                   { sum = val;               return this; }
        public Builder concatString(String val)          { concatString = val;      return this; }

        public BfhlResponse build() { return new BfhlResponse(this); }
    }
}
