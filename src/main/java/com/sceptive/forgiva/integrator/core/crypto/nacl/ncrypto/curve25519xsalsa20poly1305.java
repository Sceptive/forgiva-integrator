
package com.sceptive.forgiva.integrator.core.crypto.nacl.ncrypto;

import java.security.SecureRandom;

public class curve25519xsalsa20poly1305
{
	public static final int crypto_secretbox_PUBLICKEYBYTES = 32;
	public static final int crypto_secretbox_SECRETKEYBYTES = 32;
	public static final int crypto_secretbox_BEFORENMBYTES = 32;
	public static final int crypto_secretbox_NONCEBYTES = 24;
	public static final int crypto_secretbox_ZEROBYTES = 32;
	public static final int crypto_secretbox_BOXZEROBYTES = 16;
	
	public static int crypto_box_getpublickey(byte[] pk, byte[] sk)
	{
		return curve25519.crypto_scalarmult_base(pk, sk);
	}
	
	public static int crypto_box_keypair(byte[] pk, byte[] sk) {
		SecureRandom rng = new SecureRandom();
		rng.nextBytes(sk);
		return curve25519.crypto_scalarmult_base(pk, sk);
	}
	
	public static int crypto_box_afternm(byte[] c, byte[] m, long mlen, byte[] n, byte[] k)
	{
		return xsalsa20poly1305.crypto_secretbox(c, m, mlen, n, k);
	}
	
	public static int crypto_box_beforenm(byte[] k, byte[] pk, byte[] sk)
	{
		byte[] sp = new byte[32], sigmap = xsalsa20.sigma;
		
		curve25519.crypto_scalarmult(sp, sk, pk);
		return hsalsa20.crypto_core(k, null, sp, sigmap);
	}
	
	public static int crypto_box(byte[] c, byte[] m, long mlen, byte[] n, byte[] pk, byte[] sk)
	{
		byte[] kp = new byte[crypto_secretbox_BEFORENMBYTES];
		
		crypto_box_beforenm(kp, pk, sk);
		return crypto_box_afternm(c, m, mlen, n, kp);
	}
	
	public static int crypto_box_open(byte[] m, byte[] c, long clen, byte[] n, byte[] pk, byte[] sk)
	{
		byte[] kp = new byte[crypto_secretbox_BEFORENMBYTES];
		
		crypto_box_beforenm(kp, pk, sk);
		return crypto_box_open_afternm(m, c, clen, n, kp);
	}
	
	public static int crypto_box_open_afternm(byte[] m, byte[] c, long clen, byte[] n, byte[] k)
	{
		return xsalsa20poly1305.crypto_secretbox_open(m, c, clen, n, k);
	}
	
	public static int crypto_box_afternm(byte[] c, byte[] m, byte[] n, byte[] k)
	{
		return crypto_box_afternm(c, m, (long)m.length, n, k);
	}
	
	public static int crypto_box_open_afternm(byte[] m, byte[] c, byte[] n, byte[] k)
	{
		return crypto_box_open_afternm(m, c, (long) c.length, n, k);
	}
	
	public static int crypto_box(byte[] c, byte[] m, byte[] n, byte[] pk, byte[] sk)
	{
		return crypto_box(c, m, (long) m.length, n, pk, sk);
	}
	
	public static int crypto_box_open(byte[] m, byte[] c, byte[] n, byte[] pk, byte[] sk)
	{
		return crypto_box_open(m, c, (long) c.length, n, pk, sk);
	}
}
