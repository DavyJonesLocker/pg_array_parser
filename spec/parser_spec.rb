require 'spec_helper'

class Parser
  include PgArrayParser
end

describe 'PgArrayParser' do
  let!(:parser) { Parser.new }

  describe '#parse_pg_array' do
    context 'one dimensional arrays' do
      context 'no strings' do
        it 'returns an array of strings' do
          parser.parse_pg_array(%[{1,2,3}]).should eq ['1','2','3']
        end
      end

      context 'strings' do
        it 'returns an array of strings when containing commas in a quoted string' do
          parser.parse_pg_array(%[{1,"2,3",4}]).should eq ['1','2,3','4']
        end

        it 'returns an array of strings when containing an escaped quote' do
          parser.parse_pg_array(%[{1,"2\\",3",4}]).should eq ['1','2",3','4']
        end
      end
    end
  end
end
