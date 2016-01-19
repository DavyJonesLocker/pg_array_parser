require File.expand_path('../pg_array_parser/version', __FILE__)

if RUBY_PLATFORM =~ /java/
  require 'jruby'
  require File.expand_path('../pg_array_parser.jar', __FILE__)
  Java::pgarrayparser.PgArrayParserEngineService.new.basicLoad JRuby.runtime
  
  module PgArrayParser
    def parse_pg_array(value)
      @parser ||= PgArrayParserEngine.new
      @parser.parse_pg_array(value)
    end
  end
else
  begin
    require 'pg_array_parser/pg_array_parser'
  rescue LoadError
    begin
      require "pg_array_parser/pg_array_parser.#{RbConfig::CONFIG['DLEXT']}"
    rescue LoadError
      require "pg_array_parser.#{RbConfig::CONFIG['DLEXT']}"
    end
  end
end
