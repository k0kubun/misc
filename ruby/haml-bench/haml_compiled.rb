begin;
  extend Haml::Helpers

  _hamlout = @haml_buffer = Haml::Buffer.new(
    haml_buffer,
    {
      autoclose: [
        "area", "base", "basefont", "br", "col", "command",
        "embed", "frame", "hr", "img", "input", "isindex",
        "keygen", "link", "menuitem", "meta", "param",
        "source", "track", "wbr",
      ],
      preserve: ["textarea", "pre", "code"],
      attr_wrapper: "'",
      ugly: true,
      format: :html5,
      encoding: "UTF-8",
      escape_html: true,
      escape_attrs: true,
      hyphenate_data_attrs: true,
      cdata: false,
    },
  )
  _erbout = _hamlout.buffer
  @output_buffer = output_buffer ||= ActionView::OutputBuffer.new rescue nil;

  header = 'Colors'
  item = [
    { name: 'red',   current: true,  url: '#red' },
    { name: 'green', current: false, url: '#green' },
    { name: 'blue',  current: false, url: '#blue' },
  ]

  _hamlout.buffer << "<!DOCTYPE html>\n<html>\n<head>\n<title>Simple Benchmark</title>\n</head>\n<body>\n<h1>".freeze
  _hamlout.buffer << _hamlout.format_script(header, false, true, false, true, false, true, true)
  _hamlout.buffer << "</h1>\n".freeze

  unless item.empty?
    _hamlout.buffer << "<ul>\n".freeze
    for i in item
      if i[:current]
        _hamlout.buffer << "<li>\n<strong>".freeze
        _hamlout.buffer << _hamlout.format_script(i[:name], false, true, false, true, false, true, true)
        _hamlout.buffer << "</strong>\n</li>\n".freeze
      else
        _hamlout.buffer << "<li>\n<a".freeze
        _hamlout.buffer << _hamlout.attributes({}, nil, href: i[:url])
        _hamlout.buffer << ">".freeze
        _hamlout.buffer << _hamlout.format_script(i[:name], false, true, false, true, false, true, true)
        _hamlout.buffer << "</a>\n</li>\n".freeze
      end
    end
    _hamlout.buffer << "</ul>\n".freeze
  else
    _hamlout.buffer << "<p>The list is empty.</p>\n".freeze
  end
  _hamlout.buffer << "</body>\n</html>\n".freeze
  _erbout
ensure
  @haml_buffer = @haml_buffer.upper if @haml_buffer
end
