require 'spec_helper'

class Parser
  include PgArrayParser
end

describe 'PgArrayParser' do
  let!(:parser) { Parser.new }

  describe '#parse_pg_array' do
    context 'one dimensional arrays' do
      it 'returns an array of strings' do
        parser.parse_pg_array(%[{1,2,3}]).should eq ['1','2','3']
      end
    end
  end
end
