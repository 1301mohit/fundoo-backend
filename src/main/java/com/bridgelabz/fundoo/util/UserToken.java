package com.bridgelabz.fundoo.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.auth0.jwt.interfaces.Verification;
import com.bridgelabz.fundoo.exception.TokenException;



public class UserToken {
	
	public static final String TOKEN_SECRET="gh2we43jue";
	public static String generateToken(Long id)
	{
		try {
			Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
			String token=JWT.create()
							.withClaim("id", id)
							.sign(algorithm);                           
			return token;		
		}
		catch(Exception exception)
		{
			//throw new Exception("Token Not Generated");
			throw new TokenException("Token Not Generated");
		}
	}
	
	public static Long tokenVerify(String token)	
	{
		Long userid;
		try {
			Verification verification=JWT.require(Algorithm.HMAC256(TOKEN_SECRET));
			JWTVerifier jwtverifier=verification.build();
			DecodedJWT decodedjwt=jwtverifier.verify(token);
			Claim claim=decodedjwt.getClaim("id");
			userid=claim.asLong();	
		}
		catch(Exception exception)
		{
			//throw new Exception("Token Not Verified");
			throw new TokenException("Token Not Verified");
		}
		
		return userid;
	}

}
