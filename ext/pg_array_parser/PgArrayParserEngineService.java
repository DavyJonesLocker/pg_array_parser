package pgarrayparser;

import java.lang.Long;
import java.io.IOException;

import org.jruby.Ruby;
import org.jruby.RubyArray;
import org.jruby.RubyClass;
import org.jruby.RubyFixnum;
import org.jruby.RubyModule;
import org.jruby.RubyObject;
import org.jruby.anno.JRubyMethod;
import org.jruby.runtime.ObjectAllocator;
import org.jruby.runtime.ThreadContext;
import org.jruby.runtime.builtin.IRubyObject;
import org.jruby.runtime.load.BasicLibraryService;

public class PgArrayParserEngineService implements BasicLibraryService {

  public boolean basicLoad(Ruby runtime) throws IOException {

    RubyModule pgArrayParser = runtime.defineModule("PgArrayParser");
    RubyClass pgArrayParserEngine = pgArrayParser.defineClassUnder("PgArrayParserEngine", runtime.getObject(), new ObjectAllocator() {
      public IRubyObject allocate(Ruby runtime, RubyClass rubyClass) {
        return new PgArrayParserEngine(runtime, rubyClass);
      }
    });

    pgArrayParserEngine.defineAnnotatedMethods(PgArrayParserEngine.class);
    return true;
  }
}
