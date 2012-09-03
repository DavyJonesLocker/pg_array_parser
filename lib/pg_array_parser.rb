require File.expand_path('../pg_array_parser/version', __FILE__)
require File.expand_path('../pg_array_parser', __FILE__)

if RUBY_PLATFORM =~ /java/
  module PgArrayParser
    require 'jruby'
    require File.expand_path('../pg_array_parser.jar', __FILE__)
    require 'pgArrayParser/pg_array_parser_engine'

    def parse_pg_array(value)
      @parser ||= PgArrayParserEngine.new
      @parser.parse_pg_array(value)
    end
  end
else
  require 'pg_array_parser/pg_array_parser'
end

