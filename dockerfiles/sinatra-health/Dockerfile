FROM k0kubun/ruby23:latest
COPY . /app
WORKDIR /app
RUN bundle install -j4
CMD ["bundle", "exec", "ruby", "app.rb"]
