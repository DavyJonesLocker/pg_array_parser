#include <ruby.h>

VALUE PgArrayParser = Qnil;

//Prototypes
VALUE read_array(int *index, char *string, int *length, char *word);
VALUE parse_pg_array(VALUE self, VALUE pg_array_string);

VALUE parse_pg_array(VALUE self, VALUE pg_array_string) {

  //convert to c-string, create a buffer of the same length, as that will be the worst case
  char *c_pg_array_string = StringValueCStr(pg_array_string);
  int array_string_length = RSTRING_LEN(pg_array_string);
  char *word = malloc(sizeof(char) * (array_string_length + 1));

  int index = 1;

  return read_array(&index, c_pg_array_string, &array_string_length, word);

}

VALUE read_array(int *index, char *c_pg_array_string, int *array_string_length, char *word)
{
  // Return value: array
  VALUE array;
  array = rb_ary_new();
  int word_index = 0;
  int openQuote = 0;
  for(;(*index) < (*array_string_length); ++(*index))
  {
    if(!openQuote && c_pg_array_string[*index] == ',' ||
        c_pg_array_string[*index] == '}' )
    {
      if(c_pg_array_string[(*index) - 1] != '"')
      {
        word[word_index] = '\0';
        word_index = 0;
        rb_ary_push(array, rb_str_new2(word));
      }
      if(c_pg_array_string[*index] == '}')
      {
        (*index)++;
        return array;
      }

    }
    else if (openQuote && c_pg_array_string[*index] == '"' && c_pg_array_string[(*index) - 1] == '\\')
    {
      word[word_index - 1] = '"';
    }
    else if (openQuote && c_pg_array_string[*index] == '"' && c_pg_array_string[(*index) - 1] != '\\')
    {
      word[word_index] = '\0';
      word_index = 0;
      openQuote = 0;
      rb_ary_push(array, rb_str_new2(word));
    }
    else if(c_pg_array_string[*index] == '"')
    {
      openQuote = 1;
    }
    else if(c_pg_array_string[*index] == '{')
    {
      (*index)++;
      rb_ary_push(array, read_array(index, c_pg_array_string, array_string_length, word));
    }
    else
    {
      word[word_index] = c_pg_array_string[*index];
      word_index++;
    }
  }

  return array;
}

void Init_pg_array_parser(void) {
  PgArrayParser = rb_define_module("PgArrayParser");
  rb_define_method(PgArrayParser, "parse_pg_array", parse_pg_array, 1);

}


