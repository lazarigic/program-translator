
package rs.ac.bg.etf.pp1;

import java_cup.runtime.Symbol;

%%

%{

	// ukljucivanje informacije o poziciji tokena
	private Symbol new_symbol(int type) {
		return new Symbol(type, yyline+1, yycolumn);
	}
	
	// ukljucivanje informacije o poziciji tokena
	private Symbol new_symbol(int type, Object value) {
		return new Symbol(type, yyline+1, yycolumn, value);
	}

%}

%cup
%line
%column

%xstate COMMENT

%eofval{
	return new_symbol(sym.EOF);
%eofval}

%%

" " 	{ }
"\b" 	{ }
"\t" 	{ }
"\r\n" 	{ }
"\f" 	{ }

  
"program"   { return new_symbol(sym.PROG, yytext());}
"print" 	{ return new_symbol(sym.PRINT, yytext()); }
"read"      { return new_symbol(sym.READ, yytext()); }
"void" 		{ return new_symbol(sym.VOID, yytext()); }
"++"		{ return new_symbol(sym.INCREMENT, yytext()); }
"--"		{ return new_symbol(sym.DECREMENT, yytext()); }
"."			{ return new_symbol(sym.DOT, yytext()); }
"new"       { return new_symbol(sym.NEW, yytext()); }
"*"			{ return new_symbol(sym.MULTIPLY, yytext()); }
"%"			{ return new_symbol(sym.MODULATE, yytext()); }
"/"			{ return new_symbol(sym.DIVIDE, yytext()); }
"+" 		{ return new_symbol(sym.PLUS, yytext()); }
"-" 		{ return new_symbol(sym.MINUS, yytext()); }
"=" 		{ return new_symbol(sym.EQUALS, yytext()); }
"+=" 		{ return new_symbol(sym.ADDEQUALS, yytext()); }
"-=" 		{ return new_symbol(sym.SUBEQUALS, yytext()); }
"*=" 		{ return new_symbol(sym.MULEQUALS, yytext()); }
"/=" 		{ return new_symbol(sym.DIVEQUALS, yytext()); }
"%=" 		{ return new_symbol(sym.MODEQUALS, yytext()); }
";" 		{ return new_symbol(sym.SEMI, yytext()); }
"," 		{ return new_symbol(sym.COMMA, yytext()); }
"(" 		{ return new_symbol(sym.L_PAREN, yytext()); }
")" 		{ return new_symbol(sym.R_PAREN, yytext()); }
"{" 		{ return new_symbol(sym.L_BRACE, yytext()); }
"}"			{ return new_symbol(sym.R_BRACE, yytext()); }
"["			{ return new_symbol(sym.L_INDEXER, yytext()); }
"]"			{ return new_symbol(sym.R_INDEXER, yytext()); }
"^"			{ return new_symbol(sym.AT, yytext()); }
"@"			{ return new_symbol(sym.REALAT, yytext()); }
"#"			{ return new_symbol(sym.TAR, yytext()); }
"$="		{ return new_symbol(sym.DOLAREQUALS, yytext()); }
"const"     { return new_symbol(sym.CONST, yytext()); }
"return"	{ return new_symbol(sym.RETURN, yytext());}


"//" {yybegin(COMMENT);}
<COMMENT> . {yybegin(COMMENT);}
<COMMENT> "\r\n" { yybegin(YYINITIAL); }

"true"|"false" {return new_symbol(sym.BOOL, new Boolean (yytext()));}
[0-9]+  { return new_symbol(sym.NUMBER, new Integer (yytext())); }
([a-z]|[A-Z])[a-z|A-Z|0-9|_]* 	{return new_symbol (sym.IDENT, yytext()); }
"'"[\040-\176]"'" {return new_symbol (sym.CHAR, new Character (yytext().charAt(1)));}
. { System.err.println("JBTLeksicka greska ("+yytext()+") u liniji "+(yyline+1)); }










