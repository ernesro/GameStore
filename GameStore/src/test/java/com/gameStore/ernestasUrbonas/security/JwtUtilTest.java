package com.gameStore.ernestasUrbonas.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class JwtUtilTest {

    private JwtUtil jwtUtil;

    @BeforeEach
    public void setUp() {

        jwtUtil = new JwtUtil();
        ReflectionTestUtils.setField(jwtUtil, "secretKey", "dGVzdFNlY3JldEtleUZvclRlc3RpbmdPbmx5MTIzNDU2Nzg=");
    }

    @Test
    public void generateToken_ShouldReturnAToken(){

        //ACT-------------------------------------------------------------------------

        String token = jwtUtil.generateToken("test", List.of("ADMIN"));

        //ASSERT----------------------------------------------------------------------

        assertThat(token).isNotNull();
        assertThat(token).isNotEmpty();
    }

   @Test
   public void validateToken_ShouldReturnTrue(){

       //ARRANGE---------------------------------------------------------------------

        String token = jwtUtil.generateToken("test", List.of("ADMIN"));

       //ACT-------------------------------------------------------------------------

        Boolean result = jwtUtil.validateToken(token);

        //ASSERT----------------------------------------------------------------------

        assertThat(result).isTrue();
   }

    @Test
    public void validateToken_ShouldReturnFalse(){

        //ARRANGE---------------------------------------------------------------------

        String token = "not.valid.token :( ";

        //ACT-------------------------------------------------------------------------

        Boolean result = jwtUtil.validateToken(token);

        //ASSERT----------------------------------------------------------------------

        assertThat(result).isFalse();
    }

    @Test
    public void getUsernameFromToken_ShouldReturnTest(){

        //ARRANGE---------------------------------------------------------------------

        String token = jwtUtil.generateToken("test", List.of("ADMIN"));

        //ACT-------------------------------------------------------------------------


        String result = jwtUtil.getUsernameFromToken(token);

        //ASSERT----------------------------------------------------------------------

        assertThat(result).isEqualTo("test");
    }

    @Test
    public void getRoleFromToken_ShouldReturnADMIN(){

        //ARRANGE---------------------------------------------------------------------

        String token = jwtUtil.generateToken("test", List.of("ADMIN"));

        //ACT-------------------------------------------------------------------------

        List<String> result = jwtUtil.getRolesFromToken(token);

        //ASSERT----------------------------------------------------------------------

        assertThat(result).contains("ADMIN");
    }
}
