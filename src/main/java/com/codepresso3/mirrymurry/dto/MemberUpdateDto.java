package com.codepresso3.mirrymurry.dto;

import com.codepresso3.mirrymurry.vo.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@NoArgsConstructor
@Getter @Setter
public class MemberUpdateDto {
    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String user_name;

    private String email;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
            message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
    private String password;

    @NotBlank(message="비밀번호를 한번 더 입력하세요")
    private String passwordCheck;

    @NotBlank(message = "우편번호는 필수 입력 값입니다.")
    private String postcode;

    @NotEmpty(message = "도로명 주소는 필수 입력 값입니다.")
    private String road_address;

    @NotEmpty(message = "상세주소는 필수 입력 값입니다.")
    private String detail_address;

    @NotBlank(message = "휴대폰 번호는 필수 입력 값입니다.")
    @Length(min= 10, max=11, message = "올바른 전화번호를 입력하세요")
    private String phone_number;

    public MemberUpdateDto(Member member) {
        this.user_name = member.getUser_name();
        this.email = member.getEmail();
        this.postcode = member.getPostcode();
        this.road_address = member.getRoad_address();
        this.detail_address = member.getDetail_address();
        this.phone_number = member.getPhone_number();
    }

    public MemberUpdateDto(String user_name, String email, String password, String passwordCheck, String postcode, String road_address, String detail_address, String phone_number) {
        this.user_name = user_name;
        this.email = email;
        this.password = password;
        this.passwordCheck = passwordCheck;
        this.postcode = postcode;
        this.road_address = road_address;
        this.detail_address = detail_address;
        this.phone_number = phone_number;
    }
}
