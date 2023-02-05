package com.codepresso3.mirrymurry.service;

import com.codepresso3.mirrymurry.dto.MemberUpdateDto;
import com.codepresso3.mirrymurry.mapper.MemberMapper;
import com.codepresso3.mirrymurry.vo.Member;
import com.google.gson.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberMapper memberMapper;

    private static String GEOCODE_URL="http://dapi.kakao.com/v2/local/search/address.json?query=";
    private static String GEOCODE_USER_INFO="KakaoAK 4da7685744deb0cba108af2b7626f33a";

    public boolean saveMember(Member member){
        validateDuplicateMember(member);
        Double lng = ((JsonObject) geocoder(member.getRoad_address()).get(0)).get("x").getAsDouble();
        Double lat = ((JsonObject) geocoder(member.getRoad_address()).get(0)).get("y").getAsDouble();
        member.setLongitude(lng);
        member.setLatitude(lat);
        Integer result = memberMapper.createMember(member);
        return result==1;
    }

    public boolean updateStore(Member member){
        Double lng = ((JsonObject) geocoder(member.getRoad_address()).get(0)).get("x").getAsDouble();
        Double lat = ((JsonObject) geocoder(member.getRoad_address()).get(0)).get("y").getAsDouble();
        member.setLongitude(lng);
        member.setLatitude(lat);
        Integer result = memberMapper.updateMember(member);
        return result==1;
    }

    public boolean updateMember(Member member, MemberUpdateDto memberUpdateDto){
        Member validMember = memberMapper.findByPhoneNumber(memberUpdateDto.getPhone_number());
        if(validMember != null && !memberUpdateDto.getPhone_number().equals(member.getPhone_number()) ){
            throw new IllegalStateException("입력하신 전화번호는 이미 가입되어 있습니다.");
        }
        member.setUser_name(memberUpdateDto.getUser_name());
        member.setPostcode(memberUpdateDto.getPostcode());
        member.setRoad_address(memberUpdateDto.getRoad_address());
        member.setDetail_address(memberUpdateDto.getDetail_address());
        member.setPhone_number(memberUpdateDto.getPhone_number());
        Double lng = ((JsonObject) geocoder(memberUpdateDto.getRoad_address()).get(0)).get("x").getAsDouble();
        Double lat = ((JsonObject) geocoder(memberUpdateDto.getRoad_address()).get(0)).get("y").getAsDouble();
        member.setLongitude(lng);
        member.setLatitude(lat);
        Integer result = memberMapper.updateMember(member);
        return result==1;
    }

    private void validateDuplicateMember(Member member){
        Member findMember = memberMapper.findByEmail(member.getEmail());
        Member findMember1 = memberMapper.findByPhoneNumber(member.getPhone_number());
        if(findMember != null){
            throw new IllegalStateException("해당 이메일은 이미 사용중입니다.");
        }
        if(findMember1 != null){
            throw new IllegalStateException("입력하신 전화번호는 이미 가입되어 있습니다.");
        }
    }

    public JsonArray geocoder(String road_address) {

        URL obj;

        try{
            String address = URLEncoder.encode(road_address, "UTF-8");

            obj = new URL(GEOCODE_URL+address);

            HttpURLConnection con = (HttpURLConnection)obj.openConnection();

            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization",GEOCODE_USER_INFO);
            con.setRequestProperty("content-type", "application/json");
            con.setDoOutput(true);
            con.setUseCaches(false);
            con.setDefaultUseCaches(false);

            Charset charset = Charset.forName("UTF-8");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), charset));

            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            JsonParser jsonParser = new JsonParser();
            JsonObject jsonObject = (JsonObject) jsonParser.parse(response.toString());
            JsonArray jsonArray = jsonObject.getAsJsonArray("documents");
            return jsonArray;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;

    }


}
