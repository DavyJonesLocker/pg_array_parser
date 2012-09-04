package pgarrayparser;

import org.jruby.*;
import org.jruby.anno.JRubyClass;
import org.jruby.anno.JRubyMethod;
import org.jruby.runtime.ThreadContext;
import org.jruby.runtime.builtin.IRubyObject;

@JRubyClass(name = "PgArrayParser::PgArrayParserEngine")
public class PgArrayParserEngine extends RubyObject {

    public PgArrayParserEngine(final Ruby runtime, RubyClass rubyClass) {
        super(runtime, rubyClass);
    }

    @JRubyMethod(name = "parse_pg_array")
    public RubyArray parse_pg_array( ThreadContext context, IRubyObject value) {
        String content = value.asJavaString();
        return parseData(context, content, 0);
    }

    private static RubyArray parseData( ThreadContext context, String content, int index)
    {
        RubyArray items = RubyArray.newArray(context.getRuntime());

        for( int x = index; x < content.length(); x++ ) {

            char token = content.charAt(x);

            switch (token) {
                case '{':
                    x = parseArrayContents( context, items, content, x + 1 );
                    break;
                case '}':
                    return items;
            }

        }

        return items;
    }

    private static int parseArrayContents( ThreadContext context, RubyArray items, String content, int index ) {

        StringBuilder currentItem = new StringBuilder();
        boolean isEscaping = false;
        boolean isQuoted = false;
        boolean wasQuoted = false;

        int x = index;

        for(; x < content.length(); x++ ) {

            char token = content.charAt(x);

            if ( isEscaping ) {
                currentItem.append( token );
                isEscaping = false;
            } else {
                if ( isQuoted ) {
                    switch (token) {
                        case '"':
                            isQuoted = false;
                            wasQuoted = true;
                            break;
                        case '\\':
                            isEscaping = true;
                            break;
                        default:
                            currentItem.append(token);
                    }
                } else {
                    switch (token) {
                        case '\\':
                            isEscaping = true;
                            break;
                        case ',':
                            addItem(context, items, currentItem, wasQuoted);
                            currentItem = new StringBuilder();
                            wasQuoted = false;
                            break;
                        case '"':
                            isQuoted = true;
                            break;
                        case '{':
                            RubyArray internalItems = RubyArray.newArray(context.getRuntime());
                            x = parseArrayContents( context, internalItems, content, x + 1 );
                            items.add(internalItems);
                            break;
                        case '}':
                            addItem(context, items, currentItem, wasQuoted);
                            return x;
                        default:
                            currentItem.append(token);
                    }
                }
            }

        }

        return x;
    }

    private static void addItem( ThreadContext context, RubyArray items, StringBuilder builder, boolean quoted ) {
        String value = builder.toString();

        if ( value.length() == 0 ) {
            return;
        }

        if ( !quoted && "NULL".equalsIgnoreCase( value ) ) {
            items.add(context.getRuntime().getNil());
        } else {
            items.add(RubyString.newString( context.getRuntime(), value ));
        }
    }

}
