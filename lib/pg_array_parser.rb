require File.expand_path('../pg_array_parser/version', __FILE__)
require File.expand_path('../pg_array_parser', __FILE__)
module PgArrayParser
  if RUBY_PLATFORM =~ /java/
    require 'jruby'
    java_import com.dockyard.PgArrayParser
    com.dockyard.PgArrayParser.PgArrayParserService.new.basicLoad(JRuby.runtime)

    def self.parse_pg_array(value)
      @parser ||= PgArrayParserEngine.new
      @parser.parse_pg_array(value)
    end
  end
end
