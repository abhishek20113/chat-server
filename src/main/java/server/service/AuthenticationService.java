package server.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.interfaces.DecodedJWT;
import redis.clients.jedis.Jedis;
import server.exceptions.AppException;

import static com.auth0.jwt.algorithms.Algorithm.HMAC256;
import static server.exceptions.ErrorCode.InvalidCredentials;
import static server.util.ApplicationConstants.FieldConstants.*;

public class AuthenticationService {

  private Jedis jedis;

  public AuthenticationService(Jedis jedis) {
    this.jedis = jedis;
  }

  public String authenticate(String userId, String password) {
    if (password.equals(getUserPassword(userId))) {
      return generateToken(userId);
    }
    throw new AppException(InvalidCredentials);
  }

  private String getUserPassword(String userId) {
    return jedis.get(String.format(USER_KEY_FORMAT, userId));
  }

  private String generateToken(String userId) {
    return JWT.create()
      .withClaim(USER_ID, userId)
      .sign(HMAC256(APP_SECRET));
  }

  public String validateUser(String jwtToken) {
    JWTVerifier verifier = JWT.require(HMAC256(APP_SECRET))
      .build();
    DecodedJWT decodedJWT = verifier.verify(jwtToken);
    return decodedJWT
      .getClaim(USER_ID)
      .asString();
  }
}
