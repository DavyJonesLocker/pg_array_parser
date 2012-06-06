#include <ruby.h>

VALUE PgArrayParser = Qnil;
VALUE parse_pg_array(VALUE self, VALUE pg_array_string) {
  VALUE array, ruby_word;
  char *c_pg_array_string, *word;
  int index, array_string_length, word_index;
  c_pg_array_string = StringValueCStr(pg_array_string);
  array_string_length = RSTRING_LEN(pg_array_string);
  word = malloc(array_string_length);
  array = rb_ary_new();

  word_index = 0;
  int openQuote = 0;
  for(index = 0;index < array_string_length; ++index)
  {
    if(openQuote && c_pg_array_string[index] != '"') {
      word[word_index] = c_pg_array_string[index];
      word_index++;
    }
    else if (openQuote && c_pg_array_string[index] == '"' && c_pg_array_string[index-1] == '\\')
    {
      word[word_index - 1] = '"';
    } 
    else if (openQuote && c_pg_array_string[index] == '"' && c_pg_array_string[index-1] != '\\')
    {
      word[word_index] = '\0';
      ruby_word = rb_str_new2(word);
      word_index = 0;
      openQuote = 0;
      rb_ary_push(array, ruby_word);
    }
    else if(c_pg_array_string[index] == '"')
    {
      openQuote = 1;
    }
    else if(c_pg_array_string[index] == ',' ||
        c_pg_array_string[index] == '}' )
    {
      if(c_pg_array_string[index - 1] != '"')
      {
        word[word_index] = '\0';
        ruby_word = rb_str_new2(word);
        word_index = 0;
        rb_ary_push(array, ruby_word);
      }
    }
    else if(c_pg_array_string[index] == '{')
    {
    }
    else
    {
      word[word_index] = c_pg_array_string[index];
      word_index++;
    }
  }

  return array;
}

void Init_pg_array_parser(void) {
  PgArrayParser = rb_define_module("PgArrayParser");
  rb_define_method(PgArrayParser, "parse_pg_array", parse_pg_array, 1);

}


