# PgArrayParser

Fast PostreSQL array parsing.

## Installation

Add this line to your application's Gemfile:

    gem 'pg_array_parser'

And then execute:

    $ bundle

Or install it yourself as:

    $ gem install pg_array_parser

## Usage

Include the `PgArrayParser` module, which provides the `parse_pg_array`
method.

    class MyPostgresParser
      include PgArrayParser
    end
    
    parser = MyPostgresParser.new
    parser.parse_pg_array '{}'
    # => []
    parser.parse_pg_array '{1,2,3,4}'
    # => ["1", "2", "3", "4"]
    parser.parse_pg_array '{1,{2,3},4}'
    # => ["1", ["2", "3"], "4"]
    parser.parse_pg_array '{some,strings that,"May have some ,\'s"}'
    # => ["some", "strings that", "May have some ,'s"]
## Authors

Dan McClain [twitter](http://twitter.com/_danmcclain) [github](http://github.com/danmcclain)

## Contributing

1. Fork it
2. Create your feature branch (`git checkout -b my-new-feature`)
3. Commit your changes (`git commit -am 'Added some feature'`)
4. Push to the branch (`git push origin my-new-feature`)
5. Create new Pull Request
