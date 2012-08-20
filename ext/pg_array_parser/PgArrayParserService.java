package com.dockyard.PgArrayParser;

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

public class PgArrayParserService implements BasicLibraryService {
  private Ruby runtime;

  public boolean basicLoad(Ruby runtime) throws IOException {
    this.runtime = runtime;

    RubyModule pgArrayParser = runtime.defineModule("PgArrayParser");
    RubyClass pgArrayParserEngine = pgArrayParser.defineClassUnder("PgArrayParserEngine", runtime.getObject(), new ObjectAllocator() {
      public IRubyObject allocate(Ruby runtime, RubyClass rubyClass) {
        return new PgArrayParserEngine(runtime, rubyClass);
      }
    });

    pgArrayParserEngine.defineAnnotatedMethods(PgArrayParserEngine.class);
    return true;
  }

  public class PgArrayParserEngine extends RubyObject {
    public PgArrayParserEngine(final Ruby runtime, RubyClass rubyClass) {
      super(runtime, rubyClass);
    }

    @JRubyMethod
    public IRubyObject parse_pg_array(IRubyObject value) {
      RubyArray test = RubyArray.newArray(runtime, 1);

      return test;

    }
  }
}
