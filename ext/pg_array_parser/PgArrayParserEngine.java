package pgarrayparser;

import org.jruby.Ruby;
import org.jruby.RubyArray;
import org.jruby.RubyClass;
import org.jruby.RubyFixnum;
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

    @JRubyMethod(name = "parse_pg_array")
    public IRubyObject parse_pg_array(ThreadContext context, IRubyObject value) {
      Ruby runtime = context.getRuntime();
      RubyArray test = RubyArray.newArray(runtime, 1);
      return test;

    }
  }
