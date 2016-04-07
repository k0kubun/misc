require 'libx11'

class XServer
  def self.with_connection
    display = LibX11.xopen_display
    yield(display)
  ensure
    LibX11.xclose_display(display)
  end
end

XServer.with_connection do |display|
end
