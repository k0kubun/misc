begin;
  extend Haml::Helpers;
  _hamlout = @haml_buffer = Haml::Buffer.new(haml_buffer, {});
  _erbout = _hamlout.buffer;;
  header = 'Colors'
  ;  item = [{ name: 'red', current: true, url: '#red' }, { name: 'green', current: false, url: '#green' }, { name: 'blue', current: false, url: '#blue' }]
  ; _hamlout.buffer << ("<!DOCTYPE html>\n<html>\n<head>\n<title>Simple Benchmark</title>\n</head>\n<body>\n<h1>".freeze);; _hamlout.buffer << ((





(a = _hamlout.html_escape(header).strip; _hamlout.toplevel? && a.include?('<textarea'.freeze);a)
  ).to_s);;
  _hamlout.buffer << ("</h1>\n".freeze);;  unless item.empty?
  ; _hamlout.buffer << ("<ul>\n".freeze);;
   for i in item
  ;  if i[:current]
  ; _hamlout.buffer << ("<li>\n<strong>".freeze);; _hamlout.buffer << ((
    (a = _hamlout.html_escape(i[:name]).strip; _hamlout.toplevel? && a.include?('<textarea'.freeze);a)
  ).to_s);;
  _hamlout.buffer << ("</strong>\n</li>\n".freeze);;  else
  ; _hamlout.buffer << ("<li>\n<a".freeze);;
  ; _haml_attribute_compiler1 = (i[:url]); case (_haml_attribute_compiler1);
  when Hash, true, false, nil;
    _hamlout.buffer << ((_hamlout.attributes({ "href".freeze => _haml_attribute_compiler1 }, nil)).to_s);;
  else;
    _hamlout.buffer << (" href='".freeze);;
    _hamlout.buffer << (::Haml::Helpers.html_escape((_haml_attribute_compiler1)));;
    _hamlout.buffer << ("'".freeze);;
  end;
  _hamlout.buffer << (">".freeze);;
  _hamlout.buffer << (
    (a = _hamlout.html_escape(i[:name]).strip; _hamlout.toplevel? && a.include?('<textarea'.freeze);a)
  );;
  _hamlout.buffer << ("</a>\n</li>\n".freeze);; end;; end;; _hamlout.buffer << ("</ul>\n".freeze);;  else
  ; _hamlout.buffer << ("<p>The list is empty.</p>\n".freeze);; end;; _hamlout.buffer << ("</body>\n</html>\n".freeze);_erbout;
ensure;
  @haml_buffer = @haml_buffer.upper if @haml_buffer;
end;
