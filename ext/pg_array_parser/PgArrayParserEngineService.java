package pgarrayparser;

import org.jruby.Ruby;
import org.jruby.RubyClass;
import org.jruby.RubyModule;
import org.jruby.runtime.ObjectAllocator;
import org.jruby.runtime.builtin.IRubyObject;
import org.jruby.runtime.load.BasicLibraryService;

import java.io.IOException;

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