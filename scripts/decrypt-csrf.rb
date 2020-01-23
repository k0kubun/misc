# Rails 5.2, but probably support more

require 'base64'
require 'cgi'

AUTHENTICITY_TOKEN_LENGTH = 32

def unmask_token(masked_token) # :doc:
  # Split the token into the one-time pad and the encrypted
  # value and decrypt it.
  one_time_pad = masked_token[0...AUTHENTICITY_TOKEN_LENGTH]
  encrypted_csrf_token = masked_token[AUTHENTICITY_TOKEN_LENGTH..-1]
  xor_byte_strings(one_time_pad, encrypted_csrf_token)
end

def xor_byte_strings(s1, s2) # :doc:
  s2_bytes = s2.bytes
  s1.each_byte.with_index { |c1, i| s2_bytes[i] ^= c1 }
  s2_bytes.pack("C*")
end

# expecting a single value in cookie. the value should be `CGI.escape`d.
xsrf_token = <<-EOS.strip
EOS
xsrf_token = CGI.unescape(xsrf_token)
masked_token = Base64.strict_decode64(xsrf_token)
csrf_token = unmask_token(masked_token)
p csrf_token
