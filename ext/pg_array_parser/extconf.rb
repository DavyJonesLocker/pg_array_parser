unless RUBY_PLATFORM =~ /java/
  require 'mkmf'
  create_makefile('pg_array_parser/pg_array_parser')
end
