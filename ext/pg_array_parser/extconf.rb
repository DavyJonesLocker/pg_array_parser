if RUBY_PLATFORM =~ /java/
else
  require 'mkmf'
  create_makefile('pg_array_parser/pg_array_parser')
end
