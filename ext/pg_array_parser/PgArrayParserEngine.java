package pgarrayparser;

import org.jruby.Ruby;
import org.jruby.RubyArray;
import org.jruby.RubyClass;
import org.jruby.RubyFixnum;
import org.jruby.RubyString;
import org.jruby.RubyNil;
import org.jruby.RubyModule;
import org.jruby.RubyObject;
import org.jruby.anno.JRubyMethod;
import org.jruby.anno.JRubyClass;
import org.jruby.runtime.ObjectAllocator;
import org.jruby.runtime.ThreadContext;
import org.jruby.runtime.builtin.IRubyObject;
import org.jruby.runtime.load.BasicLibraryService;

  @JRubyClass(name = "PgArrayParser::PgArrayParserEngine")
  public class PgArrayParserEngine extends RubyObject {
    public PgArrayParserEngine(final Ruby runtime, RubyClass rubyClass) {
      super(runtime, rubyClass);
    }

    private Ruby runtime;
    private int index;
    private String arrayString;
    @JRubyMethod(name = "parse_pg_array")
    public IRubyObject parse_pg_array(ThreadContext context, IRubyObject value) {
      runtime = context.getRuntime();
      index = 1;
      arrayString = value.asJavaString();
      IRubyObject returnValue = readArray();
      return returnValue;

    }

    private RubyArray readArray()
    {
      RubyArray array = RubyArray.newArray(runtime, 1);

      int openQuote = 0;
      boolean escapeNext = false;
      char currentChar;
      StringBuilder word = new StringBuilder();

      if(index < arrayString.length() && arrayString.charAt(index) == '}')
      {
        return array;
      }

      for(;index < arrayString.length(); ++index)
      {
        currentChar = arrayString.charAt(index);
        if(openQuote < 1)
        {
          if(currentChar == ',' || currentChar == '}')
          {
            if(!escapeNext)
            {
              if(openQuote == 0 && word.length() == 4 && word.toString().equals("NULL"))
              {
                array.append(runtime.getNil());
              }
              else
              {
                array.append(RubyString.newString(runtime, word.toString()));
              }
            }
            if(currentChar == '}')
            {
              return array;
            }
            escapeNext = false;
            openQuote = 0;
            word = new StringBuilder();
          }
          else if(currentChar == '"')
          {
            openQuote = 1;
          }
          else if(currentChar == '{')
          {
            index++;
            array.append(readArray());
            escapeNext = true;
          } 
          else
          {
            word.append(String.valueOf(currentChar));
          }
        }
        else if(escapeNext)
        {
          word.append(String.valueOf(currentChar));
          escapeNext = false;
        }
        else if(currentChar == '\\')
        {
          escapeNext = true;
        }
        else if(currentChar == '"')
        {
          openQuote = -1;
        }
        else
        {
          word.append(String.valueOf(currentChar));
        }
      }

      return array;
    }
  }
