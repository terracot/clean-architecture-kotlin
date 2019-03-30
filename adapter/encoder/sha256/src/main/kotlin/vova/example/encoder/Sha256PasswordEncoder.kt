package vova.example.encoder

import org.apache.commons.codec.digest.DigestUtils
import vova.example.domain.port.PasswordEncoder

class Sha256PasswordEncoder : PasswordEncoder {
    override fun encode(str: String): String {
        return DigestUtils.sha256Hex(str)
    }
}
