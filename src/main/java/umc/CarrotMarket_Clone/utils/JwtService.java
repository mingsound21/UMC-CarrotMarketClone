package umc.CarrotMarket_Clone.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import umc.CarrotMarket_Clone.config.BaseException;
import umc.CarrotMarket_Clone.config.secret.Secret;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

import static umc.CarrotMarket_Clone.config.BaseResponseStatus.EMPTY_JWT;
import static umc.CarrotMarket_Clone.config.BaseResponseStatus.INVALID_JWT;

@Service
public class JwtService {

    /**
    JWT 생성
    @param userIdx
    @return String
     */
    public String createJwt(Long userIdx){
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam("type","jwt")
                .claim("userIdx",userIdx) // payload에 담는 정보의 한 조각 = claim, claim = name/valud 한쌍으로 이루어짐. 토큰에는 여러개의 claim 넣을 수 있음.
                .setIssuedAt(now)
                .setExpiration(new Date(System.currentTimeMillis()+15*(1000*60))) // 15분 유효로 변경! 원래 : System.currentTimeMillis()+1*(1000*60*60*24*365) -> 아마 365일인 듯
                .signWith(SignatureAlgorithm.HS256, Secret.JWT_SECRET_KEY)
                .compact();
    }

    /**
     * jwt refresh 토큰 생성
     */
    public String createRefreshToken(Long userIdx) {
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam("type","jwt")
                .claim("userIdx",userIdx)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + System.currentTimeMillis()+60*10000)) // 현재로부터 10분 뒤까지
                .signWith(SignatureAlgorithm.HS256, Secret.JWT_REFRESH_SECRET_KEY)
                .compact();
    }

    /**
     * refresh token의 유효성 확인 = 만료 시간 지났는지 확인
     */
    public String validateRefreshToken(){
        // 1. Refresh Jwt 추출
        String refreshToken = getRefreshJwt();

        try{
            // 2. Jwt parsing & 만료 시간 확인
            Jws<Claims> claims = Jwts.parser().setSigningKey(Secret.JWT_REFRESH_SECRET_KEY).parseClaimsJws(refreshToken);
//refresh 토큰의 만료시간이 지나지 않았을 경우, 새로운 access 토큰을 생성합니다.
            if (!claims.getBody().getExpiration().before(new Date())) {
                return createJwt(claims.getBody().get("userIdx", Long.class));
            }
        }catch (Exception e){
            return null;
        }
        return null;
    }

    /**
    Header에서 X-ACCESS-TOKEN 으로 JWT 추출
    @return String
     */
    public String getJwt(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return request.getHeader("Authorization");
    }

    /**
     Header에서 X-REFRESH-TOKEN 으로 refresh JWT 추출
     @return String
     */
    public String getRefreshJwt(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return request.getHeader("X-REFRESH-TOKEN");
    }

    /**
    JWT에서 userIdx 추출
    @return int
    @throws BaseException
     */
    public Long getUserIdx() throws BaseException {
        //1. JWT 추출
        String accessToken = getJwt();
        System.out.println("accessToken = " + accessToken);
        if(accessToken == null || accessToken.length() == 0){
            throw new BaseException(EMPTY_JWT);
        }

        // 2. JWT parsing
        Jws<Claims> claims;
        try{
            claims = Jwts.parser()
                    .setSigningKey(Secret.JWT_SECRET_KEY)
                    .parseClaimsJws(accessToken);
        } catch (Exception ignored) {
            throw new BaseException(INVALID_JWT);
        }

        // 3. userIdx 추출
        return claims.getBody().get("userIdx",Long.class);  // jwt 에서 userIdx를 추출합니다.
    }


    /* 토큰 유효기간 확인*/
    public boolean validateAccessToken() throws Exception{
        String accessToke = getJwt();
        boolean expiration = Jwts.parser()
                .setSigningKey(Secret.JWT_SECRET_KEY)
                .parseClaimsJws(accessToke)
                .getBody()
                .getExpiration().before(new Date());
        return !expiration;

    }
}