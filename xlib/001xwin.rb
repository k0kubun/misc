require 'libx11'

WINDOW_POS_X  = 100
WINDOW_POS_Y  = 100
WINDOW_WIDTH  = 400
WINDOW_HEIGHT = 300
BORDER_WIDTH  = 2

display = LibX11.xopen_display('')

root   = display.default_root_window
screen = display.default_screen

white = display.white_pixel(screen)
black = display.black_pixel(screen)

window = LibX11.xcreate_simple_window(
  display, root,
  WINDOW_POS_X, WINDOW_POS_Y, WINDOW_WIDTH, WINDOW_HEIGHT,
  BORDER_WIDTH, black, white,
)

display.xselect_input(window, LibX11::XEvent::KEY_PRESS_MASK)
display.xmap_window(window)

loop do
  event = LibX11.xnext_event(display)

  case event.type
  when LibX11::XEvent::KEY_PRESS
    LibX11.xdestroy_window(display, window)
    LibX11.xclose_display(display)
    break
  end
end
