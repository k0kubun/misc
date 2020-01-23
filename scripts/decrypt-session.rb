# Rails 5.2

require 'cgi'
require 'json'
require 'active_support'

def verify_and_decrypt_session_cookie(cookie, secret_key_base)
  cookie        = CGI.unescape(cookie)
  salt          = 'encrypted cookie'
  signed_salt   = 'signed encrypted cookie'
  key_generator = ActiveSupport::KeyGenerator.new(secret_key_base, iterations: 1000)
  secret        = key_generator.generate_key(salt)[0, ActiveSupport::MessageEncryptor.key_len]
  sign_secret   = key_generator.generate_key(signed_salt)
  encryptor     = ActiveSupport::MessageEncryptor.new(secret, sign_secret)

  encryptor.decrypt_and_verify(cookie)
end

# expecting a single value in cookie. the value should be `CGI.escape`d.
cookie = <<-EOS.strip
EOS
secret_key_base = ''

session = verify_and_decrypt_session_cookie(cookie, secret_key_base)
p session
p Base64.strict_decode64(session['_csrf_token']) # this prints bytes, not texts
