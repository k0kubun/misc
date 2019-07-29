require 'capybara-webkit'
require 'capybara/dsl'
require 'erb'
require 'json'
require 'net/http'
require 'uri'

UNAVAILABLE_DATES = [
  'August 3, 2019',
  'August 4, 2019',
  'August 10, 2019',
  'August 11, 2019',
  'September 21, 2019',
  'September 22, 2019',
  'September 28, 2019',
  'September 29, 2019',
]

KNOWN_PLACES = [
  /\ATokyo Yaesu Minamiguchi Testing Center/,
  /\AIkebukuro Community College PC College Seibu Ikebukuro/,
  /\AKashiwaeki Minamiguchi Testing Center/,
  #/\AYokohama-eki Kita Higashiguchi Testing Center/,
  /\AHongo Sanchome \[Kurosebia Hongo Bldg.\] Testing Center/,
  /\AHongo Sanchome Testing Center/,
  /\AIchikawa Ekimae Testing Center/,
  /\AYazawagakuen Kikuna Testing Center/,
  /\ANihonbashi Ningyocho Testing Center/,
  /\ABenefull School Iidabashi/,
  /\AUbint Iidabashi Testing Center/,
  /\ATabata Test Center/,
  /\ANakano Eki Minamiguchi Testing Center/,
  /\AOmiyaeki Higashiguchi Testing Center/,
  /\AAbiko Tennodai Testing Center/,
  /\AWaseda University/,
  /\AYoyogi Ekimae Testing Center/,
]

class TestCenterSearcher
  include Capybara::DSL

  ETS_ORG = 'https://v2.ereg.ets.org'

  def initialize
    Capybara.current_driver = :webkit
    Capybara.run_server = false
    Capybara::Webkit.configure do |config|
      config.allow_url('*')
    end
  end

  def search
    puts '1. Open search page'
    visit "#{ETS_ORG}/ereg/public/workflowmanager/schlWorkflow?_p=TEL"

    puts '2. Set August/September'
    if Time.now.month == 7
      find('#duration .rightArrow.right').click
      sleep 1
    end

    puts '3. Search with Akihabara'
    fill_in('location', with: 'Akihabara')
    sleep 0.5
    find('#location_listbox li[data-offset-index="0"]').click
    sleep 0.5

    puts '4. Submit search'
    click_button('findTestCenterButton')
    sleep 1

    #open page.save_screenshot
    puts '5. Open accordions'
    find('#byDateAccordion .panel:nth-child(1) .accordion-toggle').click
    sleep 1

    page.body.match(/var findSeatResponse =(?<response>{.+?});/)&.[](:response)
  end


  private

  def open(file)
    system('xdg-open', file)
  end
end

module SlackWebhook
  SLACK_WEBHOOK_URL = ENV.fetch('SLACK_WEBHOOK_URL')

  def self.notify(message)
    puts message

    payload = {
      text: message,
      channel: '#toefl',
      link_names: true,
    }

    uri = URI.parse(SLACK_WEBHOOK_URL)
    Net::HTTP.start(uri.host, uri.port, use_ssl: uri.scheme == 'https') do |http|
      http.post(uri.path, payload.to_json, { 'Content-Type' => 'application/json' })
    end.tap(&:value)
  end
end

begin
  # Get findSeatResponse.json
  seat_json = TestCenterSearcher.new.search
  if seat_json.nil?
    puts "Failed to get findSeatResponse!"
    return
  end

  # Normalize to `{ date => [place] }`
  date_places = {}
  seat_info = JSON.parse(seat_json, symbolize_names: true)
  seat_info.fetch(:sortedDates).each do |date_info|
    display_date = date_info.fetch(:displayDate)
    next if UNAVAILABLE_DATES.any? { |date| display_date.start_with?(date) }

    place_names = date_info.fetch(:items).map { |i| i.fetch(:name) }
      .reject { |place| KNOWN_PLACES.any? { |reg| reg.match(place) } }
    unless place_names.empty?
      date_places[display_date] = place_names
    end
  end

  # Build Slack message
  if date_places.empty?
    message = "#{Time.now.getlocal('+09:00').strftime('%Y-%m-%d (%a) %H:%M:%S %z')}: Not found"
  else
    message = ERB.new(<<~EOS, trim_mode: '%').result
    #=====================================================
    # <%= Time.now.getlocal('+09:00').strftime('%Y-%m-%d (%a) %H:%M:%S %z') %>
    #=====================================================
    % date_places.each do |date, places|
    ■ *<%= date %>*
    %   places.each do |place|
    * <%= place %>
    %   end

    % end
    @k0kubun
    EOS
  end

  # Notify Slack
  SlackWebhook.notify(message)
rescue => e
  SlackWebhook.notify(e.full_message)
end
