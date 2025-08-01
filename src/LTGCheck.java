import java.io.*;
import java.net.*;
import java.util.stream.*;
import java.util.zip.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.regex.Matcher;
import org.bzdev.util.*;


public class LTGCheck {

    private static char graveAccent(char ch) {
	switch (ch) {
	case 'a':
	    return '\u00E0';
	case 'A':
	    return '\u00C0';
	case 'e':
	    return '\u00E8';
	case 'E':
	    return '\u00C8';
	case 'i':
	    return '\u00EC';
	case 'I':
	    return '\u00CC';
	case 'o':
	    return '\u00F2';
	case 'O':
	    return '\u00D2';
	case 'u':
	    return '\u00F9';
	case 'U':
	    return '\u00D9';
	default:
	    return ch;
	}
    }

    private static char acuteAccent(char ch) {
	switch (ch) {
	case 'a':
	    return '\u00E1';
	case 'A':
	    return '\u00C1';
	case 'e':
	    return '\u00E9';
	case 'E':
	    return '\u00C9';
	case 'i':
	    return '\u00ED';
	case 'I':
	    return '\u00CD';
	case 'o':
	    return '\u00F3';
	case 'O':
	    return '\u00D3';
	case 'u':
	    return '\u00FA';
	case 'U':
	    return '\u00DA';
	default:
	    return ch;
	}
    }

    private static char circumflex(char ch) {
	switch (ch) {
	case 'a':
	    return '\u00E2';
	case 'A':
	    return '\u00C2';
	case 'e':
	    return '\u00EA';
	case 'E':
	    return '\u00CA';
	case 'i':
	    return '\u00EE';
	case 'I':
	    return '\u00CE';
	case 'o':
	    return '\u00F4';
	case 'O':
	    return '\u00D4';
	case 'u':
	    return '\u00FB';
	case 'U':
	    return '\u00Db';
	default:
	    return ch;
	}
    }

    private static char diaeresis(char ch) {
	switch (ch) {
	case 'a':
	    return '\u00E4';
	case 'A':
	    return '\u00C4';
	case 'e':
	    return '\u00EB';
	case 'E':
	    return '\u00CB';
	case 'i':
	    return '\u00EF';
	case 'I':
	    return '\u00CF';
	case 'o':
	    return '\u00F6';
	case 'O':
	    return '\u00D6';
	case 'u':
	    return '\u00FC';
	case 'U':
	    return '\u00DC';
	default:
	    return ch;
	}
    }

    private static char tilde(char ch) {
	switch (ch) {
	case 'a':
	    return '\u00E3';
	case 'A':
	    return '\u00C3';
	case 'n':
	    return '\u00F1';
	case 'N':
	    return '\u00D1';
	case 'o':
	    return '\u00F5';
	case 'O':
	    return '\u00D5';
	default:
	    return ch;
	}
    }



    private static  char umlaut(char ch) {
	switch (ch) {
	case 'a':
	    return '\u00E4';
	case 'A':
	    return '\u00C4';
	case 'e':
	    return '\u00EB';
	case 'E':
	    return '\u00CB';
	case 'i':
	    return '\u00EF';
	case 'I':
	    return '\u00CF';
	case 'o':
	    return '\u00F6';
	case 'O':
	    return '\u00D6';
	case 'u':
	    return '\u00FC';
	case 'U':
	    return '\u00DC';
	default:
	    return ch;
	}
    }

    private static enum PatternType {
	EOL,
	OBRACE,
	CBRACE,
	BEGIN_DOC,
	END_DOC,
	FRONTMATTER,
	MAKETITLE,
	TINY,
	SCRIPTSIZE,
	FOOTNOTESIZE,
	SMALL,
	NORMALSIZE,
	LARGE1,
	LARGE2,
	LARGE3,
	HUGE1,
	HUGE2,
	CHAPTER,
	TOC,
	LOF,
	MAINMATTER,
	BEGIN_QUOTE,
	END_QUOTE,
	BEGIN_CODE,
	END_CODE,
	SECTION,
	SUBSECTION,
	SUBSUBSECTION,
	BEGIN_ITEMIZE,
	END_ITEMIZE,
	BEGIN_ENUMERATE,
	END_ENUMERATE,
	ITEM,
	ITEM_BRACKET,
	BEGIN_CENTER,
	END_CENTER,
	TEXTBF,
	TEXTTT,
	TEXTIT,
	TEXTUP,
	TEXTSL,
	TEXTSC,
	TEXTMD,
	TEXTRM,
	TEXTSF,
	EMPH,
	TEXTNORMAL,
	MDSERIES,
	BF,
	BFSERIES,
	RMSERIES,
	SLFAMILY,
	TT,
	TTFAMILY,
	UPSHAPE,
	ITSERIES,
	SLSHAPE,
	SCSHAPE,
	NORMALFONT,
	BEGIN_VERBATIM,
	END_VERBATIM,
	BEGIN_VERBATIM_STAR,
	END_VERBATIM_STAR,
	CAPTION,
	CAPTION1,
	FOOTNOTE,
	VERB,
	BEGIN_TABBING,
	KILL,
	END_TABBING,
	BEGIN_TABULAR,
	END_TABULAR,
	BEGIN_TABULAR_STAR,
	END_TABULAR_STAR,
	SZ,
	SS,
	SS_STAR,
	OPEN_QUOTE,
	CLOSE_QUOTE,
	INLINE_EQ,
	DOLLAR,
	AMPERSAND,
	CROSSHATCH,
	UNDERSCORE,
	OPEN_BRACE,
	CLOSING_BRACE,
	BEGIN_DMATH,
	END_DMATH,
	BEGIN_DMATH1,
	END_DMATH1,
	BEGIN_EQ,
	END_EQ,
	BEGIN_EQ1,
	END_EQ1,
	BEGIN_EQA,
	END_EQA,
	BEGIN_EQA_STAR,
	END_EQA_STAR,
	COMMENT,
	PERCENT,
	BEGIN_FIG,
	END_FIG,
	BEGIN_TABLE,
	END_TABLE,
	DEF,
	THEPAGE,
	LABEL,
	REF,
	PAGEREF,
	TILDE,
	PLUS,
	CBRACKET,
	SLASHDQ,
	SLASHSQ,
	SLASHBQ,
	SLASHHAT,
	SLASHTILDE,
	SLASHH,
	SLASHDQ1,
	SLASHSQ1,
	SLASHBQ1,
	SLASHHAT1,
	SLASHTILDE1,
	SLASHO,
	SLASHo,
	SLASHAE,
	SLASHae,
	SLASHAA,
	SLASHaa,
	SLASHss,
	BEGIN_DESCR,
	END_DESCR,
	BEGIN_VERSE,
	END_VERSE,
	INPUT,
	INCLUDE,
	INCLUDE_ONLY,
	INCLUDEGRAPHICS,
	BEGIN_ANY,		// unknown environments
	END_ANY,
	TITLE,
	AUTHOR,
	DATE,
	RESIZEBOX,
	LDOTS,
	SOFT_HYPHEN,
	NEWCOMMAND,
	RENEWCOMMAND,
	PROVIDECOMMAND,
	NEWENVIRONMENT,
	RENEWENVIRONMENT,
	USEPACKAGE,
	USEPACKAGE1,
	SLASH_ANY
    }

    private static class Pattern {
	PatternType type;
	String pattern;
	public String getCommand() {
	    if (pattern.startsWith("\\begin{")) {
		if (type == PatternType.BEGIN_ANY) return null;
		return pattern.substring(7, pattern.length()-1);
	    } else if (pattern.startsWith("\\end{")) {
		if (type == PatternType.END_ANY) return null;
		return pattern.substring(5, pattern.length()-1);
	    } else {
		int plenm1 = pattern.length() - 1;
		if (pattern.charAt(plenm1) =='{') {
		    return pattern.substring(0, plenm1);
		} else {
		    return pattern;
		}
	    }
	}

	public Pattern(PatternType type, String pattern) {
	    this.type = type;
	    this.pattern = pattern;
	}
    }

    private static final Pattern[] latexPatterns = {
	new Pattern(PatternType.EOL, "\n"),
	new Pattern(PatternType.OBRACE, "{"),
	new Pattern(PatternType.CBRACE, "}"),
	new Pattern(PatternType.TEXTTT, "\\texttt{"),
	new Pattern(PatternType.BEGIN_DOC, "\\begin{document}"),
	new Pattern(PatternType.END_DOC, "\\end{document}"),
	new Pattern(PatternType.FRONTMATTER, "\\frontmatter"),
	new Pattern(PatternType.MAKETITLE, "\\maketitle"),
	new Pattern(PatternType.TINY, "\\tiny"),
	new Pattern(PatternType.SCRIPTSIZE, "\\scriptsize"),
	new Pattern(PatternType.FOOTNOTESIZE, "\\footnotesize"),
	new Pattern(PatternType.SMALL, "\\small"),
	new Pattern(PatternType.NORMALSIZE, "\\normalsize"),
	new Pattern(PatternType.LARGE1, "\\large"),
	new Pattern(PatternType.LARGE2, "\\Large"),
	new Pattern(PatternType.LARGE3, "\\LARGE"),
	new Pattern(PatternType.HUGE1, "\\huge"),
	new Pattern(PatternType.HUGE2, "\\Huge"),
	new Pattern(PatternType.CHAPTER, "\\chapter{"),
	new Pattern(PatternType.TOC, "\\tableofcontents"),
	new Pattern(PatternType.LOF, "\\listoffigures"),
	new Pattern(PatternType.MAINMATTER, "\\mainmatter"),
	new Pattern(PatternType.BEGIN_QUOTE, "\\begin{quote}"),
	new Pattern(PatternType.END_QUOTE, "\\end{quote}"),
	// new Pattern(PatternType.BEGIN_CODE, "\\begin{code}"),
	// new Pattern(PatternType.END_CODE, "\\end{code}"),
	new Pattern(PatternType.SECTION, "\\section{"),
	new Pattern(PatternType.SUBSECTION, "\\subsection{"),
	new Pattern(PatternType.SUBSUBSECTION, "\\subsubsection{"),
	new Pattern(PatternType.BEGIN_ITEMIZE, "\\begin{itemize}"),
	new Pattern(PatternType.END_ITEMIZE, "\\end{itemize}"),
	new Pattern(PatternType.BEGIN_ENUMERATE, "\\begin{enumerate}"),
	new Pattern(PatternType.END_ENUMERATE, "\\end{enumerate}"),
	new Pattern(PatternType.ITEM, "\\item"),
	new Pattern(PatternType.ITEM_BRACKET, "\\item["),
	new Pattern(PatternType.BEGIN_CENTER, "\\begin{center}"),
	new Pattern(PatternType.END_CENTER, "\\end{center}"),
	new Pattern(PatternType.TEXTBF, "\\textbf{"),
	new Pattern(PatternType.TEXTIT, "\\textit{"),
	new Pattern(PatternType.TEXTUP,"\\textup{"),
	new Pattern(PatternType.TEXTSL,"\\textsl{"),
	new Pattern(PatternType.TEXTSC,"\\textsc{"),
	new Pattern(PatternType.TEXTMD,"\\textmd{"),
	new Pattern(PatternType.TEXTBF,"\\textbf{"),
	new Pattern(PatternType.TEXTRM,"\\textrm{"),
	new Pattern(PatternType.TEXTSF,"\\textsf{"),
	new Pattern(PatternType.EMPH, "\\emph{"),
	new Pattern(PatternType.TEXTNORMAL, "\\textnormal{"),
	new Pattern(PatternType.MDSERIES, "\\mdseries"),
	new Pattern(PatternType.BF, "\\bf"),
	new Pattern(PatternType.BFSERIES, "\\bfseries"),
	new Pattern(PatternType.RMSERIES, "\\rmseries"),
	new Pattern(PatternType.SLFAMILY, "\\slfamily"),
	new Pattern(PatternType.TT, "\\tt"),
	new Pattern(PatternType.TTFAMILY, "\\ttfamily"),
	new Pattern(PatternType.UPSHAPE, "\\upshape"),
	new Pattern(PatternType.ITSERIES, "\\itseries"),
	new Pattern(PatternType.SLSHAPE, "\\slshape"),
	new Pattern(PatternType.SCSHAPE, "\\scshape"),
	new Pattern(PatternType.NORMALFONT, "\\normalfont"),
	new Pattern(PatternType.BEGIN_VERBATIM, "\\begin{verbatim}"),
	new Pattern(PatternType.END_VERBATIM, "\\end{verbatim}"),
	new Pattern(PatternType.BEGIN_VERBATIM_STAR, "\\begin{verbatim*}"),
	new Pattern(PatternType.END_VERBATIM_STAR, "\\end{verbatim*}"),
	new Pattern(PatternType.CAPTION, "\\caption{"),
	new Pattern(PatternType.CAPTION1, "\\caption["),
	new Pattern(PatternType.FOOTNOTE, "\\footnote{"),
	new Pattern(PatternType.VERB, "\\verb+"),
	new Pattern(PatternType.BEGIN_TABBING, "\\begin{tabbing}"),
	new Pattern(PatternType.KILL, "\\kill"),
	new Pattern(PatternType.END_TABBING, "\\end{tabbing}"),
	new Pattern(PatternType.BEGIN_TABULAR, "\\begin{tabular}"),
	new Pattern(PatternType.END_TABULAR, "\\end{tabular}"),
	new Pattern(PatternType.BEGIN_TABULAR_STAR, "\\begin{tabular*}"),
	new Pattern(PatternType.END_TABULAR_STAR, "\\end{tabular*}"),
	new Pattern(PatternType.SZ, "\\ss"),
	new Pattern(PatternType.SS, "\\\\"),
	new Pattern(PatternType.SS_STAR, "\\\\*["),
	new Pattern(PatternType.OPEN_QUOTE, "``"),
	new Pattern(PatternType.CLOSE_QUOTE, "''"),
	new Pattern(PatternType.INLINE_EQ, "$"),
	new Pattern(PatternType.DOLLAR, "\\$"),
	new Pattern(PatternType.AMPERSAND, "\\&"),
	new Pattern(PatternType.CROSSHATCH, "\\#"),
	new Pattern(PatternType.UNDERSCORE, "\\_"),
	new Pattern(PatternType.OPEN_BRACE, "\\{"),
	new Pattern(PatternType.CLOSING_BRACE, "\\}"),
	new Pattern(PatternType.BEGIN_DMATH, "\\begin{displaymath}"),
	new Pattern(PatternType.BEGIN_DMATH1, "\\["),
	new Pattern(PatternType.END_DMATH, "\\end{displaymath}"),
	new Pattern(PatternType.END_DMATH1, "\\]"),
	new Pattern(PatternType.BEGIN_EQ, "\\begin{equation}"),
	new Pattern(PatternType.END_EQ, "\\end{equation}"),
	new Pattern(PatternType.BEGIN_EQ1, "\\("),
	new Pattern(PatternType.END_EQ1, "\\)"),
	new Pattern(PatternType.BEGIN_EQA, "\\begin{eqnarray}"),
	new Pattern(PatternType.END_EQA, "\\end{eqnarray}"),
	new Pattern(PatternType.BEGIN_EQA_STAR, "\\begin{eqnarray*}"),
	new Pattern(PatternType.END_EQA_STAR, "\\end{eqnarray*}"),
	new Pattern(PatternType.COMMENT, "%"),
	new Pattern(PatternType.PERCENT, "\\%"),
	new Pattern(PatternType.BEGIN_FIG, "\\begin{figure}"),
	new Pattern(PatternType.END_FIG, "\\end{figure}"),
	new Pattern(PatternType.BEGIN_TABLE, "\\begin{table}"),
	new Pattern(PatternType.END_TABLE, "\\end{table}"),
	new Pattern(PatternType.DEF, "\\def"),
	new Pattern(PatternType.THEPAGE, "\\thepage{"),
	new Pattern(PatternType.LABEL, "\\label{"),
	new Pattern(PatternType.REF, "\\ref{"),
	new Pattern(PatternType.PAGEREF, "\\pageref{"),
	new Pattern(PatternType.TILDE, "~"),
	new Pattern(PatternType.PLUS, "+"),
	new Pattern(PatternType.CBRACKET, "]"),
	new Pattern(PatternType.SLASHDQ, "\\\"{"),
	new Pattern(PatternType.SLASHSQ, "\\'{"),
	new Pattern(PatternType.SLASHBQ, "\\`{"),
	new Pattern(PatternType.SLASHHAT, "\\^{"),
	new Pattern(PatternType.SLASHTILDE, "\\~{"),
	new Pattern(PatternType.SLASHH, "\\H{"),
	new Pattern(PatternType.SLASHDQ1, "\\\""),
	new Pattern(PatternType.SLASHSQ1, "\\'"),
	new Pattern(PatternType.SLASHBQ1, "\\`"),
	new Pattern(PatternType.SLASHHAT1, "\\^"),
	new Pattern(PatternType.SLASHTILDE1, "\\~"),
	new Pattern(PatternType.SLASHO, "\\O"),
	new Pattern(PatternType.SLASHo, "\\o"),
	new Pattern(PatternType.SLASHAE, "\\AE"),
	new Pattern(PatternType.SLASHae, "\\ae"),
	new Pattern(PatternType.SLASHAA, "\\AA"),
	new Pattern(PatternType.SLASHaa, "\\aa"),
	new Pattern(PatternType.INPUT, "\\input{"),
	new Pattern(PatternType.INCLUDE, "\\include{"),
	new Pattern(PatternType.INCLUDE_ONLY, "\\includeonly{"),
	new Pattern(PatternType.INCLUDEGRAPHICS, "\\includegraphics{"),
	new Pattern(PatternType.BEGIN_ANY, "\\begin{"),
	new Pattern(PatternType.END_ANY, "\\end{"),
	new Pattern(PatternType.TITLE, "\\title{"),
	new Pattern(PatternType.AUTHOR, "\\author{"),
	new Pattern(PatternType.DATE, "\\date"),
	new Pattern(PatternType.RESIZEBOX, "\\resizebox{"),
	new Pattern(PatternType.LDOTS, "\\ldots"),
	new Pattern(PatternType.SOFT_HYPHEN, "\\-"),
	new Pattern(PatternType.NEWCOMMAND, "\\newcommand{"),
	new Pattern(PatternType.RENEWCOMMAND, "\\renewcommand{"),
	new Pattern(PatternType.PROVIDECOMMAND, "\\providecommand{"),
	new Pattern(PatternType.NEWENVIRONMENT, "\\newenvironment{"),
	new Pattern(PatternType.RENEWENVIRONMENT, "\\renewenvironment{"),
	new Pattern(PatternType.USEPACKAGE, "\\usepackage{"),
	new Pattern(PatternType.USEPACKAGE1, "\\usepackage["),
	new Pattern(PatternType.SLASH_ANY, "\\")
    };

    private static Pattern textPatterns[] = {
	new Pattern(PatternType.EOL, "\n")
    };

    private static Object queryServer(URL url, String data) throws Exception {
	HttpURLConnection c = null;
	try {
	    c = (HttpURLConnection) url.openConnection();
	    c.setRequestMethod("POST");
	} catch(Exception e) {
	    System.err.println("ltgcheck: " + e.getMessage());
	    System.exit(1);
	}
	String req = "language=en-US&text="
	    + URLEncoder.encode(data, "UTF-8");
	c.setRequestProperty("Content-Type",
			    "application/x-www-form-urlencoded");
	c.setRequestProperty("Content-Length",
			    "" + req.length());
	c.setDoOutput(true);
	
	InputStream is = null;

	try {
	    c.connect();
	    OutputStream os = c.getOutputStream();
	    os.write(req.getBytes("UTF-8"));
	    os.flush();
	    os.close();
	} catch (Exception e) {
	    System.err.println("ltgcheck: " + e.getMessage());
	    System.exit(1);
	}
	// System.out.println("response code = " + c.getResponseCode());
	try {
	    is = c.getInputStream();
	} catch (Exception e) {}
	if (is == null) {
	    System.err.println("ltgcheck: server response code = "
			       + c.getResponseCode());
	    try {
		is = c.getErrorStream();
		if (is == null) {
		    System.err.println("ltgcheck: Cannot find error stream");
		    System.exit(1);
		} else {
		    is.transferTo(System.err);
		    is.close();
		}
	    } catch (Exception e) {
		System.err.println("ltgcheck: Could not read an error stream");
		System.exit(1);
	    }
	    return null;
	} else {
	    return JSUtilities.JSON.parse(is, "UTF-8");
	}
    }

    private static class NoteEntry {
	int lineno;
	String note;
	public NoteEntry(int lineno, String note) {
	    this.lineno = lineno;
	    this.note = note;
	}

	public int getKey() {return lineno;}
	public String getValue() {return note;}
    }

    private static TreeMap<Integer,Integer> offsetMap = new TreeMap<>();
    private static TreeMap<Integer,String> footnoteMap = new TreeMap<>();
    private static TreeMap<Integer,String> captionMap = new TreeMap<>();

    // captions and footnotes.
    private static ArrayList<NoteEntry> noteList = new ArrayList<>();

    private static TreeSet<String> localWords = new TreeSet<String>();

    private static char lastVisCharBefore(String text, int offset,
					  boolean nltest) {
	offset--;
	if (offset < 0) return '\0';
	char ch = text.charAt(offset);;
	int nlcount = 0;
	while (Character.isWhitespace(ch)) {
	    if (ch == '\n') nlcount++;
	    offset--;
	    if (offset < 0) {
		break;
	    }
	    ch = text.charAt(offset);
	}
	if (offset < 0) return '\0';
	if (nltest == false) return ch;
	if (nlcount > 1) {
	    return '.';
	} else {
	    return ch;
	}
    }

    private static int nlCount(String text, int offset) {
	int tlen = text.length();
	int nlcount = 0;
	if (offset < tlen) {
	    char ch = text.charAt(offset);
	    while (Character.isWhitespace(ch)
		   || ch == '\\' || ch == '%') {
		if (ch == '\n') {
		    nlcount++;
		} else if (ch == '\\') {
		    return nlcount;
		} else if (ch == '%') {
		    offset++;
		    while (offset < tlen && ch != '\n') {
			offset++;
			ch = text.charAt(offset);
		    }
		}
		offset++;
		if (offset >= tlen) {
		    return nlcount;
		} else {
		    ch = text.charAt(offset);
		}
	    }
	    return nlcount;
	} else {
	    return 0;
	}
    }

    private static class Directive {
	PatternType type;
	String cmd;
	int nargs;
	boolean replace;
	String replacement;

	public Directive(PatternType type, String cmd, int nargs,
			 boolean replace,
			 String replacement)
	{
	    this.type = type;
	    this.cmd = cmd;
	    this.nargs = nargs;
	    this.replace = replace;
	    this.replacement = replacement;
	}

	public String toString() {
	    return "[LTGCheck.Directive type = " + type
		+ ", cmd = " + cmd
		+", nargs = " + nargs
		+", replace = " + replace
		+", replacement = \"" + replacement + "\"]";
	}
    }

    private static class Directives {
	HashMap<PatternType,Directive> typeMap = new HashMap<>();
	HashMap<String,Directive> cmdMap = new HashMap<>();
	public Directive get(PatternType type, String cmd) {
	    Directive directive;
	    switch(type) {
	    case SLASH_ANY:
	    case BEGIN_ANY:
		directive = cmdMap.get(cmd);
		break;
	    default:
	        directive = typeMap.get(type);
	    }
	    if (directive == null) {
		directive = new Directive(type, cmd, 0, false, "");
	    }
	    return directive;
	}
	public void put(PatternType type, String cmd, int nargs,
			boolean replace, String replacement)
	{
	    Directive directive = new
		Directive(type, cmd, nargs, replace, replacement);
	    if (cmd.charAt(0) == '\\') {
		if (type == PatternType.SLASH_ANY) {
		    cmdMap.put(cmd, directive);
		} else {
		    typeMap.put(type, directive);
		}
	    } else {
		if (type == PatternType.BEGIN_ANY) {
		    cmdMap.put(cmd, directive);
		} else {
		    typeMap.put(type, directive);
		}
	    }
	}
    }
    private static Directives directives = new Directives();

    private static void processDirective(Pattern[] patterns, ACMatcher matcher,
					  String cmd, int nargs,
					  boolean replace,
					  String replacement)
	throws IllegalArgumentException
    {
	PatternType type = null;
	if (cmd.charAt(0) == '\\') {
	    type = PatternType.SLASH_ANY;
	    for (ACMatcher.MatchResult mr: matcher.stream(cmd + "{")
		 .sorted((m1, m2) -> {
			 int s1 = m1.getStart();
			 int s2 = m2.getStart();
			 if (s1 != s2) {
			     return (s1 - s2);
			 } else {
			     int e1 = m1.getEnd();
			     int e2 = m2.getEnd();
			     return (e2 - e1);
			 }
		     })
		     .toArray(ACMatcher.MatchResult[]::new)) {
		int index = mr.getIndex();
		type = patterns[index].type;
		break;
	    }
	} else {
	    String txt = "\\begin{" + cmd +"}";
	    type = PatternType.BEGIN_ANY;
	    for (ACMatcher.MatchResult mr: matcher.stream(txt)
		 .sorted((m1, m2) -> {
			 int s1 = m1.getStart();
			 int s2 = m2.getStart();
			 if (s1 != s2) {
			     return (s1 - s2);
			 } else {
			     int e1 = m1.getEnd();
			     int e2 = m2.getEnd();
			     return (e2 - e1);
			 }
		     })
		     .toArray(ACMatcher.MatchResult[]::new)) {
		int index = mr.getIndex();
		type = patterns[index].type;
		break;
	    }
	}
	directives.put(type, cmd, nargs, replace, replacement);
    }


    private static ArrayList<String>
	scan(Pattern[] patterns, int initialLineNo, boolean skip, String text)
    {
	offsetMap.clear();
	/*
	for (Pattern p: patterns) {
	    switch(p.type) {
	    case TEXTTT:
		directives.put(PatternType.TEXTTT, "\\texttt", 1, true,
			       "(text)");
		break;
	    case BEGIN_TABULAR:
		directives.put(PatternType.BEGIN_TABULAR, "tabular", 0, true,
			       "(skipping)");
		break;
	    default:
		break;
	    }
	}
	*/
	return scan(patterns, initialLineNo, skip, text, 0);
    }
    private static ArrayList<String>
	scan(Pattern[] patterns, int initialLineNo, boolean skip, String text,
	     int scandepth)
    {
	ArrayList<String>result = new ArrayList<>();

	ACMatcher matcher = new
	    ACMatcher((spec) -> {return spec.pattern;}, patterns);

	StringBuilder sb = new StringBuilder(text.length());
	
	int lineno = initialLineNo;
	int depth = 0;
	int skipToDepth = -1;
	int footnoteDepth = -1;
	int captionDepth = -1;
	int textStart = 0;
	int textEnd = 0;
	int verbatimTextStart = 0;
	int fnStart = 0;
	int capStart = 0;
	int fnLineNo = 0;
	int capLineNo = 0;
	boolean inBracket = false;
	// boolean skipping = true;
	int skipping = skip? 1: 0;
	boolean includeMode = (!skip && scandepth == 0);

	boolean inVerb = false;
	boolean in_SS_STAR = false;
	boolean in_INLINE_EQ = false;
	boolean in_COMMENT = false;
	boolean in_VERBATIM = false;
	boolean in_VERBATIM_STAR = false;
	int verbatimSBLEN = 0;
	int lastend = 0;
	boolean umlaut = false;
	boolean acute = false;
	boolean grave = false;
	boolean hat = false;
	boolean tilde = false;
	boolean diaeresis = false;
	int anydepth = 0;
	boolean skipCBRACE = false;
	int chapterCount = 0;
	int base = 0;
	boolean endDocSeen = false;
	boolean startDocSeen = false;
	int startOfComment = -1;
	int cbraceCount = 0;
	int rbdepth = 0;
	boolean needCaptionOBrace = false;
	boolean endedQCETF = false;
	int  qcetfdepth = 0;
	int qcetfNLCount = 0;
	boolean qcetfSentenceEnded = true;
	int tabdepth = 0;
	int taboffset = 0;
	int tabnlcount = 0;
	Stack<Directive> directiveStack = new Stack<>();
	// String cmd;

	for (ACMatcher.MatchResult mr: 	matcher.stream(text)
		 .sorted((m1, m2) -> {
			 int s1 = m1.getStart();
			 int s2 = m2.getStart();
			 if (s1 != s2) {
			     return (s1 - s2);
			 } else {
			     int e1 = m1.getEnd();
			     int e2 = m2.getEnd();
			     return (e2 - e1);
			 }
		     })
		 .toArray(ACMatcher.MatchResult[]::new)) {

	    int index = mr.getIndex();
	    PatternType type = patterns[index].type;
	    String command = patterns[index].getCommand();
	    if (in_VERBATIM
		&& type != PatternType.END_VERBATIM
		&& type != PatternType.END_VERBATIM_STAR
		&& type != PatternType.EOL) {
		continue;
	    } else if (inVerb
		&& type != PatternType.PLUS
		&& type != PatternType.EOL) {
		continue;
	    } else if (startDocSeen && endDocSeen && !in_COMMENT
		&& type != PatternType.COMMENT
		&& type != PatternType.EOL) {
		continue;
	    } else if (needCaptionOBrace
		       && type != PatternType.OBRACE
		       && type != PatternType.CBRACKET
		       && type != PatternType.EOL) {
		continue;
	    }
	    int start = mr.getStart();
	    int end = mr.getEnd();
	    /*
	    System.out.println("lineno = "+ lineno
			       + ": type = " + type
			       + ", depth = " + depth
			       + ", captionDepth = " + captionDepth
			       + ", footnoteDepth = " + footnoteDepth);
	    */
	    if (depth < 0) {
		throw new IllegalStateException("depth == " + depth
						+ " at lineno " + lineno
						+ ", type = "
						+ type);
	    }

	    if (start < lastend) {
		continue;
	    }
	    if (in_COMMENT && type != PatternType.EOL) {
		continue;
	    }

	    /*
	    System.out.println("processing " + type
			       +" " + start + " " + end);
	    */
	    lastend = end;
	    switch(type) {
	    case EOL:
		tabnlcount++;
		if (in_VERBATIM || inVerb) {
		    if (verbatimSBLEN > 0) {
			if (scandepth == 0) {
			    offsetMap.put(base + verbatimSBLEN-1, lineno);
			}
			verbatimSBLEN  = 0;
		    }
		    lineno++;
		    continue;
		}
		if (skipping > 0) {
		    textStart = start;
		}
		if (in_COMMENT) {
		    in_COMMENT = false;
		    skipping--;
		    String line = text.substring(startOfComment, start)
			.replaceAll("\\h+"," ")
			.replaceAll("^ ", "")
			.replaceAll("^%+\\h?", "");
		    // Look for emacs local words.
		    // System.out.println("line = " + line);
		    if ((includeMode || !startDocSeen || endDocSeen)
			    && line.startsWith("LocalWords: ")) {
			line = line.substring("LocalWords: ".length());
			String[] words = line.split(" ");
			for (String word: words) {
			    if (word.length() != 0) {
				localWords.add(word);
			    }
			}
		    } else if (line.startsWith("ltgcheck: ")) {
			line = line.substring("ltgcheck: ".length());
			String[] words = line.split(" ");
			int len = words.length;
			if (len == 1) {
			    String cmd = words[0];
			    processDirective(patterns, matcher, cmd,
					     0, false, "");
			} else if (len > 1) {
			    String cmd = words[0];
			    if (words[1].equals("hide")) words[1] = "true";
			    else if (words[1].equals("show"))
				words[1] = "false";
			    boolean replace = Boolean
				.parseBoolean(words[1]);
			    String replacement = words.length == 2? "":
				words[2];
			    for (int i = 3; i < words.length; i++) {
				replacement = replacement
				    + (" " + words[i]);
			    }
			    processDirective(patterns, matcher, cmd, 1,
					     replace, replacement);
			}
		    }
		    if (endDocSeen) {
			continue;
		    }
		    sb.append("\n");
		    if (scandepth == 0) {
			offsetMap.put(base + sb.length()-1, lineno);
		    }
		    textStart = end;
		    lineno++;
		    continue;
		}
		sb.append(text.substring(textStart, start));
		// remove any trailing white space on this line.
		int len = sb.length();
		while (len > 0) {
		    char ch = sb.charAt(len-1);
		    if (ch != ' ' && ch != '\t') {
			break;
		    }
		    len--;
		    sb.setLength(len);
		}
		sb.append('\n');
		if (qcetfNLCount == 1) {
		    sb.append("() ");
		    qcetfNLCount = 0;
		}
		if (scandepth == 0) {
		    offsetMap.put(base + sb.length()-1, lineno);
		}
		textStart = end;
		lineno++;
		break;
	    case OBRACE:
		if (needCaptionOBrace) {
		    if (inBracket) break;
		    capStart = end;
		    capLineNo = lineno;
		    textStart = end;
		    // depth was incremented by CAPTION1
		    needCaptionOBrace = false;
		    break;
		}
		if (skipping == 0) {
		    sb.append(text.substring(textStart, start));
		    textStart = end;
		}
		depth++
;
		break;
	    case CBRACE:
		if (skipCBRACE) {
		    // Because of BEGIN_ANY, END_ANY, and include cmds
		    skipCBRACE = false;
		    continue;
		}
		depth--;
		if (skipping == 0) {
		    String s = text.substring(textStart, start);
		    if (acute) {
			if (s.length() == 1) {
			    char ch = s.charAt(0);
			    sb.append(acuteAccent(ch));
			} else {
			    sb.append(s);
			}
		    } else if (umlaut) {
			if (s.length() == 1) {
			    char ch = s.charAt(0);
			    sb.append(umlaut(ch));
			} else {
			    sb.append(s);
			}
		    } else if (grave) {
			if (s.length() == 1) {
			    char ch = s.charAt(0);
			    sb.append(graveAccent(ch));
			} else {
			    sb.append(s);
			}
		    } else if (hat) {
			if (s.length() == 1) {
			    char ch = s.charAt(0);
			    sb.append(circumflex(ch));
			} else {
			    sb.append(s);
			}
		    } else if (tilde) {
			if (s.length() == 1) {
			    char ch = s.charAt(0);
			    sb.append(tilde(ch));
			} else {
			    sb.append(s);
			}
		    } else if (diaeresis) {
			if (s.length() == 1) {
			    char ch = s.charAt(0);
			    sb.append(diaeresis(ch));
			} else {
			    sb.append(s);
			}
		    } else {
			sb.append(s);
		    }
		    textStart = end;
		} else {
		    if (cbraceCount > 0 && depth == rbdepth) {
			cbraceCount--;
			if (cbraceCount == 0) {
			    rbdepth = 0;
			    skipping--;
			    if (skipping == 0) {
				textStart = end;
			    }
			}
			continue;
		    }
		    if (depth == captionDepth) {
			String caption = text.substring(capStart, start);
			// captionMap.put(capLineNo, caption);
			ArrayList<String> caparray =
			    scan(patterns, capLineNo, false, caption,
				 scandepth+1);
			if (caparray.size() > 0) {
			    /*
			    System.out.println("caparray.get(0) = " +
					       caparray.get(0));
			    */
			    // footnoteMap.put(fnLineNo, fnarray.get(0));
			    noteList.add(new NoteEntry(capLineNo,
						       caparray.get(0)));
			}
			captionDepth = -1;
		    }
		    if (depth == footnoteDepth) {
			String fn = text.substring(fnStart, start);
			/*
			System.out.println("footnote"
					   + " (depth = " + depth + "): " + fn);
			System.out.println("footnote printed");
			*/
			ArrayList<String> fnarray =
			    scan(patterns, fnLineNo, false, fn, scandepth+1);
			if (fnarray.size() > 0) {
			    /*
			    System.out.println("line " + lineno
					       + ": fnarray.get(0) = " +
					       fnarray.get(0));
			    */
			    // footnoteMap.put(fnLineNo, fnarray.get(0));
			    noteList.add(new NoteEntry(fnLineNo,
						       fnarray.get(0)));
			}
			if (!qcetfSentenceEnded) {
			    qcetfNLCount = nlCount(text, end);
			    if (skipping == 1) {
				// we incremented skipping in FOOTNOTE
				if (qcetfNLCount == 0) {
				    char ch = text.charAt(end);
				    if (ch == ' ' || ch == '\t') {
					sb.append("()");
				    } else {
					sb.append("() ");
				    }
				}
				endedQCETF = true;
			    }
			    qcetfSentenceEnded = true;
			    if (qcetfNLCount != 1) qcetfNLCount = 0;
			}
			footnoteDepth = -1;
		    }
		    if (depth == skipToDepth) {
			skipping--;
			textStart = end;
			skipToDepth = -1;
			// The following handles a corner case where a
			// command spanned multiple lines was hidden,
			// and is immediately followed by a period.
			// Otherwise one might see a misleading error
			// message about spaces before a period.
			int sbend = sb.length()-1;
			if (skipping == 0
			    && sbend >= 0 && textStart < text.length()
			    && text.charAt(textStart) == '.'
			    && sb.charAt(sbend) == '\n') {
			    sb.append("()");
			}
		    }
		}
		acute = false;
		umlaut = false;
		grave = false;
		hat = false;
		umlaut = false;
		diaeresis = false;
		break;
	    case CAPTION:
		if (skipping == 0) {
		    sb.append(text.substring(textStart, start));
		    skipToDepth = depth;
		    captionDepth = depth;
		    skipping++;
		    capStart = end;
		    capLineNo = lineno;
		} else {
		    captionDepth = depth;
		    capStart = end;
		    capLineNo = lineno;
		}
		depth++;
		textStart = end;
		break;
	    case CAPTION1:
		if (skipping == 0) {
		    sb.append(text.substring(textStart, start));
		    needCaptionOBrace = true;
		    skipToDepth = depth;
		    captionDepth = depth;
		    skipping++;
		} else {
		    captionDepth = depth;
		    needCaptionOBrace = true;
		}
		inBracket = true;
		depth++;
		textStart = end;
		break;
	    case FOOTNOTE:
		if (skipping == 0) {
		    qcetfSentenceEnded =
			(lastVisCharBefore(text, start, true) == '.');
		    sb.append(text.substring(textStart, start));
		    skipToDepth = depth;
		    footnoteDepth = depth;
		    skipping++;
		    fnStart = end;
		    fnLineNo = lineno;
		} else if (captionDepth == -1) {
		    footnoteDepth = depth;
		    fnStart = end;
		    fnLineNo = lineno;
		}
		depth++;
		textStart = end;
		break;
	    case BEGIN_DOC:
		startDocSeen = true;
		// System.out.println("startDocSeen = " + startDocSeen);
		skipping = 0;
		textStart = end;
		// System.out.println("textStart = " + textStart);
		break;
	    case END_DOC:
		if (skipping == 0) {
		    sb.append(text.substring(textStart, start));
		    result.add(sb.toString());
		    // System.out.println("endDocSeen = " + endDocSeen);
		}
		endDocSeen = true;
		//return result;
		continue;
	    case MAKETITLE:
	    case FRONTMATTER:
	    case TOC:
	    case LOF:
	    case MAINMATTER:
	    case TINY:
	    case SCRIPTSIZE:
	    case FOOTNOTESIZE:
	    case SMALL:
	    case NORMALSIZE:
	    case LARGE1:
	    case LARGE2:
	    case LARGE3:
	    case HUGE1:
	    case HUGE2:
	    case SOFT_HYPHEN:
		if (skipping == 0) {
		    sb.append(text.substring(textStart, start));
		    textStart = end;
		}
		break;
	    case TEXTTT:
	    case TEXTBF:
	    case TEXTSC:
	    case TEXTNORMAL:
	    case TEXTIT:
	    case TEXTUP:
	    case TEXTSL:
	    case TEXTMD:
	    case TEXTRM:
	    case TEXTSF:
	    case EMPH:
		if (skipping == 0) {
		    Directive directive = directives.get(type, command);
		    sb.append(text.substring(textStart, start));
		    if (directive.replace) {
			sb.append(directive.replacement);
		    }
		    textStart = end;
		    if(directive.replace) {
			skipToDepth = depth;
			skipping++;
		    }
		}
		depth++;
		break;
	    case SLASHDQ:
		if (skipping == 0) {
		    sb.append(text.substring(textStart, start));
		    textStart = end;
		    umlaut = true;
		}
		depth++;
		break;
	    case SLASHSQ:
		if (skipping == 0) {
		    sb.append(text.substring(textStart, start));
		    textStart = end;
		    acute = true;
		}
		depth++;
		break;
	    case SLASHBQ:
		if (skipping == 0) {
		    sb.append(text.substring(textStart, start));
		    textStart = end;
		    grave = true;
		}
		depth++;
		break;
	    case SLASHHAT:
		if (skipping == 0) {
		    sb.append(text.substring(textStart, start));
		    textStart = end;
		    hat = true;
		}
		depth++;
		break;
	    case SLASHTILDE:
		if (skipping == 0) {
		    sb.append(text.substring(textStart, start));
		    textStart = end;
		    tilde = true;
		}
		depth++;
		break;
	    case SLASHH:
		if (skipping == 0) {
		    sb.append(text.substring(textStart, start));
		    textStart = end;
		    diaeresis = true;
		}
		depth++;
		break;
	    case MDSERIES:
	    case BF:
	    case BFSERIES:
	    case RMSERIES:
	    case SLFAMILY:
	    case TT:
	    case TTFAMILY:
	    case UPSHAPE:
	    case ITSERIES:
	    case SLSHAPE:
	    case SCSHAPE:
	    case NORMALFONT:
		if (skipping == 0) {
		    int tlen = text.length();
		    if (end < tlen) {
			if(Character.isLetter(text.charAt(end))) {
			    while (Character.isLetter(text.charAt(end))) {
				end++;
			    }
			}
			if (end < tlen) {
			    char ch = text.charAt(end);
			    if (ch == ' ' || ch == '\t') {
				end++;
			    }
			}
		    }
		    sb.append(text.substring(textStart,start));
		    textStart = end;
		}
		break;
	    case SLASHDQ1:
		if (skipping == 0) {
		    sb.append(text.substring(textStart, start));
		    char ch = text.charAt(end);
		    if (ch != '{') {
			sb.append(umlaut(ch));
		    } else {
			umlaut = true;
			depth++;
		    }
		    textStart = end+1;
		}
		break;
	    case SLASHSQ1:
		if (skipping == 0) {
		    sb.append(text.substring(textStart, start));
		    char ch = text.charAt(end);
		    if (ch != '{') {
			sb.append(acuteAccent(text.charAt(end)));
		    } else {
			acute = true;
			depth++;
		    }
		    textStart = end + 1;
		}
		break;
	    case SLASHBQ1:
		if (skipping == 0) {
		    sb.append(text.substring(textStart, start));
		    char ch = text.charAt(end);
		    if (ch != '{') {
			sb.append(graveAccent(text.charAt(end)));
		    } else {
			grave = true;
			depth++;
		    }
		    textStart = end + 1;
		}
		break;
	    case SLASHHAT1:
		if (skipping == 0) {
		    sb.append(text.substring(textStart, start));
		    char ch = text.charAt(end);
		    if (ch != '{') {
			sb.append(circumflex(text.charAt(end)));
		    } else {
			acute = true;
			depth++;
		    }
		    textStart = end + 1;
		}
		break;
	    case SLASHTILDE1:
		if (skipping == 0) {
		    sb.append(text.substring(textStart, start));
		    char ch = text.charAt(end);
		    if (ch != '{') {
			sb.append(tilde(text.charAt(end)));
		    } else {
			tilde = true;
			depth++;
		    }
		    textStart = end + 1;
		}
		break;
	    case CHAPTER:
		if (skipping == 0) {
		    sb.append(text.substring(textStart, start));
		    textStart = end;
		    skipToDepth = depth;
		    skipping++;
		    // if (chapterCount > 0) {
		    String s = sb.toString();
		    sb.append("\n");
		    if (scandepth == 0) {
			offsetMap.put(base+sb.length()-1, lineno);
		    }
		    base += sb.length() - 1;
		    result.add(s);
		    sb.setLength(0);
			// }
			// chapterCount++;
		}
		depth++;
		break;
	    case SECTION:
	    case SUBSECTION:
	    case SUBSUBSECTION:
		if (skipping == 0) {
		    sb.append(text.substring(textStart, start));
		    textStart = end;
		    skipToDepth = depth;
		    skipping++;
		}
		depth++;
		break;
	    case LABEL:
	    case THEPAGE:
	    case USEPACKAGE:
		if (skipping == 0) {
		    sb.append(text.substring(textStart, start));
		    textStart = end;
		    skipToDepth = depth;
		    skipping++;
		}
		depth++;
		break;
	    case USEPACKAGE1:
		{
		    int tlen = text.length();
		    while (end < tlen) {
			char ch = text.charAt(end);
			end++;
			if (ch == '}') {
			    break;
			}
		    }
		    if (skipping == 0) {
			sb.append(text.substring(textStart, start));
		    }
		    textStart = end;
		    lastend = end;
		}
		break;
		/*
	    case TEXTTT:
	    case TEXTBF:
	    case TEXTSC:
		if (skipping == 0) {
		    Directive directive = directives.get(type, command);
		    sb.append(text.substring(textStart, start));
		    // sb.append("(text)");
		    if (directive.replace) {
			sb.append(directive.replacement);
		    }
		    textStart = end;
		    if (directive.replace) {
			skipToDepth = depth;
			skipping++;
		    }
		}
		depth++;
		break;
		*/
	    case BEGIN_ITEMIZE:
	    case BEGIN_ENUMERATE:
		if (skipping == 0) {
		    sb.append(text.substring(textStart, start));
		    textStart = end;
		}
		break;
	    case END_ITEMIZE:
	    case END_ENUMERATE:
		if (skipping == 0) {
		    qcetfSentenceEnded =
			(lastVisCharBefore(text, start, true) == '.');
		    if (!qcetfSentenceEnded) {
			qcetfNLCount = nlCount(text, end);
			if (qcetfNLCount == 0) {
			    sb.append("() ");
			}
		    }
		    sb.append(text.substring(textStart, start));
		    textStart = end;
		    if (qcetfNLCount != 1) qcetfNLCount = 0;
		}
		break;
	    case BEGIN_QUOTE:
	    case BEGIN_CENTER:
	    case BEGIN_TABLE:
	    case BEGIN_FIG:
		{
		    if (qcetfdepth == 0) {
			qcetfSentenceEnded =
			    (lastVisCharBefore(text, start, true) == '.');
		    }
		    Directive directive = directives
			.get(type, text.substring(start+7, end-1));
		    directiveStack.push(directive);
		    if (skipping == 0) {
			sb.append(text.substring(textStart, start));
			textStart = end;
			if (qcetfdepth == 0) {
			    sb.append("()");
			}
			if (directive.replace) {
			    sb.append(directive.replacement);
			    skipping++;
			}
		    }
		    qcetfdepth++;
		}
		break;
	    case END_QUOTE:
	    case END_CENTER:
	    case END_TABLE:
	    case END_FIG:
		{
		    qcetfdepth--;
		    if (qcetfdepth == 0 && !qcetfSentenceEnded) {
			qcetfNLCount = nlCount(text, end);
		    }
		    if (skipping == 0) {
			if (!qcetfSentenceEnded && qcetfdepth == 0
			    && qcetfNLCount == 0) {
			    sb.append("() ");
			}
			sb.append(text.substring(textStart, start));
			textStart = end;
			endedQCETF = true;
		    }
		    Directive directive = directiveStack.pop();
		    if (directive.replace) {
			skipping--;
			textStart = end;
			if (skipping == 0) {
			    endedQCETF = true;
			}
		    }
		    if (qcetfdepth == 0) {
			qcetfSentenceEnded = true; // implies do nothing
		    }
		    if (qcetfNLCount != 1) qcetfNLCount = 0;
		}
		break;
	    case ITEM:
		if (skipping == 0) {
		    sb.append(text.substring(textStart, start));
		    sb.append("(item)");
		    textStart = end;
		}
		break;
	    case ITEM_BRACKET:
		if (skipping == 0) {
		    sb.append(text.substring(textStart, start));
		    sb.append("(");
		    textStart = end;
		    char ch = text.charAt(textStart);
		    while (ch != ']') {
			if (ch == '\\') {
			    textStart++;
			}
			ch = text.charAt(++textStart);
		    }
		    sb.append(text.substring(end, textStart));
		    sb.append(")");
		    textStart++;
		}
		break;
	    case PAGEREF:
	    case REF:
		if (skipping == 0) {
		    sb.append(text.substring(textStart, start));
		    sb.append("1");
		    textStart = end;
		    skipToDepth = depth;
		    skipping++;
		}
		depth++;
		// skipping++;
		break;
	    case TITLE:
	    case AUTHOR:
	    case DATE:
		if (skipping == 0) {
		    sb.append(text.substring(textStart, start));
		    textStart = end;
		}
		depth++;
		break;
	    case RESIZEBOX:
		if (skipping == 0) {
		    sb.append(text.substring(textStart, start));
		    sb.append("(box)");
		    textStart = end;
		    cbraceCount = 3;
		    rbdepth = depth;
		    skipping++;;
		}
		depth++;
		break;
	    case LDOTS:
		if (skipping == 0) {
		    sb.append(text.substring(textStart, start));
		    sb.append('\u2026'); // ellipsis
		    textStart = end;
		}
		break;
	    case BEGIN_DMATH:
	    case BEGIN_EQ:
	    case BEGIN_EQA:
	    case BEGIN_EQA_STAR:
		if (qcetfdepth == 0) {
		    qcetfSentenceEnded =
			(lastVisCharBefore(text, start,true) == '.');
		}
		if (skipping == 0) {
		    sb.append(text.substring(textStart, start));
		    sb.append("(x=1)");
		    textStart = end;
		}
		qcetfdepth++;
		skipping++;
		break;
	    case BEGIN_EQ1:
	    case BEGIN_DMATH1:
		if (skipping == 0) {
		    sb.append(text.substring(textStart, start));
		    sb.append("(x=1)");
		    textStart = end;
		}
		skipping++;
		break;
	    case TILDE:
		if (skipping == 0) {
		    sb.append(text.substring(textStart, start));
		    sb.append(" ");
		    textStart = end;
		}
		break;
	    case BEGIN_TABBING:
		{
		    if (skipping == 0) {
			sb.append(text.substring(textStart, start));
			textStart = end;
		    }
		    Directive directive = directives
			.get(type, "tabbing");
		    directiveStack.push(directive);
		    if (directive.replace) {
			skipping++;
			sb.append(directive.replacement);
		    }
		    taboffset = sb.length();
		    tabnlcount = 0;
		    tabdepth++;
		}
		break;
	    case KILL:
		if (skipping == 0 && tabdepth > 0) {
		    sb.setLength(taboffset);
		    for (int i = 0; i < tabnlcount; i++) {
			sb.append('\n');
		    }
		}
		textStart = end;
		break;
	    case END_TABBING:
		{
		    tabdepth--;
		    tabnlcount = 0;
		    if (skipping == 0) {
			sb.append(text.substring(textStart, start));
		    }
		    Directive directive = directiveStack.pop();
		    if (directive.replace) {
			skipping--;
		    }
		    textStart = end;
		}
		break;
	    case BEGIN_TABULAR:
	    case BEGIN_TABULAR_STAR:
		// case BEGIN_CODE:
		{
		    Directive directive =(type == PatternType.BEGIN_TABULAR)?
			directives.get(PatternType.BEGIN_TABULAR, "tabular"):
			directives.get(PatternType.BEGIN_TABULAR_STAR,
				       "tabular*");
		    directiveStack.push(directive);
		    if (skipping == 0) {
			sb.append(text.substring(textStart, start));
			if (directive.replace) {
			    sb.append(directive.replacement);
			}
			// sb.append("(skipping)");
			int tlen = text.length();
			// tabular takes and argument, which we have
			// to skip.
			char ch = (end < tlen)? text.charAt(end): '\0';
			while (end < tlen && ch != '{') {
			    end++;
			    ch = (end < tlen)? text.charAt(end): '\0';
			}
			if (ch == '{') {
			    int tdepth = 1;
			    while (end < tlen && tdepth > 0) {
				end++;
				ch = (end < tlen)? text.charAt(end): '\0';
				if (ch == '%') {
				    // skip comment (pathological case)
				    while (end < tlen && ch != '\n') {
					end++;
					ch = (end < tlen)? text.charAt(end):
					    '\0';
				    }
				} else if (ch == '{') {
				    tdepth++;
				} else if (ch == '}') {
				    tdepth--;
				} else if (ch == '\n') {
				    lineno++;
				}
			    }
			    if (end < tlen) end++;
			    // have to skip over some brackets, etc.
			    lastend = end;
			}
			textStart = end;
		    }
		    if (directive.replace) {
			skipping++;
		    }
		}
		break;
	    case PLUS:
		if (inVerb) {
		    textStart = end;
		    skipping--;
		    inVerb = false;
		}
		break;
	    case VERB:
		if (skipping == 0) {
		    sb.append(text.substring(textStart, start));
		    sb.append("(skipping)");
		    inVerb = true;
		    verbatimSBLEN = sb.length();
		    skipping++;
		}
		break;
	    case BEGIN_VERBATIM:
	    case BEGIN_VERBATIM_STAR:
		if (skipping == 0) {
		    sb.append(text.substring(textStart, start));
		    Directive directive =
			(type == PatternType.BEGIN_VERBATIM)?
			directives.get(PatternType.BEGIN_VERBATIM, "verbatim"):
			directives.get(PatternType.BEGIN_VERBATIM_STAR,
				       "verbatim*");
		    if (directive.replace) {
			sb.append(directive.replacement);
		    }
		    // sb.append("(skipping)");
		    verbatimSBLEN = sb.length();
		    verbatimTextStart = end;
		    in_VERBATIM = true;
		    in_VERBATIM_STAR =
			(type == PatternType.BEGIN_VERBATIM_STAR);
		    textStart = end;
		}
		skipping++;
		break;
	    case END_EQ:
	    case END_DMATH:
	    case END_EQA:
	    case END_EQA_STAR:
		skipping--;
		qcetfdepth--;
		if (qcetfdepth == 0 && !qcetfSentenceEnded) {
		    qcetfNLCount = nlCount(text, end);
		}
		if (skipping == 0) {
		    if (!qcetfSentenceEnded && qcetfdepth == 0
			&& qcetfNLCount == 0) {
			sb.append("() ");
		    }
		    endedQCETF = true;
		}
		textStart = end;
		if (qcetfdepth == 0) {
		    qcetfSentenceEnded = true; // implies do nothing
		}
		if (qcetfNLCount != 1) qcetfNLCount = 0;
		break;
	    case END_TABULAR:
	    case END_TABULAR_STAR:
		{
		    if (skipping == 0) {
			sb.append(text.substring(textStart, start));
		    }
		    Directive directive = directiveStack.pop();
		    if (directive.replace) {
			skipping--;
		    }
		    textStart = end;
		}
		break;
	    case END_EQ1:
	    case END_DMATH1:
		// case END_CODE:
		skipping--;
		textStart = end;
		break;
	    case END_VERBATIM:
		if (in_VERBATIM_STAR) {
		    break;
		}
	    case END_VERBATIM_STAR:
		{
		    if (skipping == 1) {
			if (type == PatternType.END_VERBATIM_STAR
			    && !in_VERBATIM_STAR) {
			    break;
			}
			Directive directive =
			    (type == PatternType.END_VERBATIM)?
			    directives.get(PatternType.BEGIN_VERBATIM,
					   "verbatim"):
			    directives.get(PatternType.BEGIN_VERBATIM_STAR,
					   "verbatim*");
			in_VERBATIM = false;
			in_VERBATIM_STAR = false;
			verbatimSBLEN = 0;
			if (!directive.replace) {
			    sb.append(text.substring(verbatimTextStart, start));
			}
			textStart = end;
		    }
		    skipping--;
		}
		break;
	    case CBRACKET:
		if (inBracket) {
		    inBracket = false;
		    if (skipping == 0) {
			textStart = end;
		    }
		} else 	if (in_SS_STAR) {
		    if (skipping > 0) {
			skipping--;
			if (skipping == 0) {
			    textStart = end;
			}
		    }
		    in_SS_STAR = false;
		}
		break;
		/*
	    case SZ:
		if (skipping == 0) {
		    sb.append(text.substring(textStart, start));
		    sb.append('\u00DF');
		    textStart = end;
		}
		break;
		*/
	    case SS_STAR:
		if (skipping == 0) {
		    sb.append(text.substring(textStart, start));
		    sb.append(' ');
		    in_SS_STAR = true;
		}
		skipping++;
		break;
	    case PERCENT:
	    case DOLLAR:
	    case AMPERSAND:
	    case CROSSHATCH:
	    case UNDERSCORE:
	    case OPEN_BRACE:
	    case CLOSING_BRACE:
		if (skipping == 0) {
		    sb.append(text.substring(textStart, start));
		    sb.append(text.charAt(end-1));
		    textStart = end;
		}
		break;
	    case SLASHO:
	    case SLASHo:
	    case SLASHAE:
	    case SLASHae:
	    case SLASHAA:
	    case SLASHaa:
	    case SZ:
		if (skipping == 0) {
		    sb.append(text.substring(textStart, start));
		    int tlen = text.length();
		    int end1 = end;
		    if (end < tlen) {
			while (end < tlen
			       && Character.isLetter(text.charAt(end))) {
			    end++;
			}
		    }
		    if (end == end1) {
			switch(type) {
			case SLASHO:
			    sb.append('\u00D8');
			    break;
			case SLASHo:
			    sb.append('\u00F8');
			    break;
			case SLASHAE:
			    sb.append('\u00C6');
			    break;
			case SLASHae:
			    sb.append('\u00E6');
			    break;
			case SLASHAA:
			    sb.append('\u00C5');
			    break;
			case SLASHaa:
			    sb.append('\u00E5');
			    break;
			case SZ:
			    sb.append('\u00DF');
			default:
			    break;
			}
			textStart = end;
		    }
		}
		break;
	    case SS:
	    case DEF:
		if (skipping == 0) {
		    sb.append(text.substring(textStart, start));
		    sb.append(' ');
		    if (type == PatternType.SS && tabdepth > 0) {
			taboffset = sb.length();
			tabnlcount = 0;
		    }
		    textStart = end;
		}
		break;
	    case OPEN_QUOTE:
	    case CLOSE_QUOTE:
		if (skipping == 0) {
		    sb.append(text.substring(textStart, start));
		    sb.append('"');
		    textStart = end;
		}
		break;
	    case INLINE_EQ:
		if (in_INLINE_EQ) {
		    skipping--;
		    if (skipping == 0) {
			textStart = end;
		    }
		    in_INLINE_EQ = false;
		} else {
		    if (skipping == 0) {
			sb.append(text.substring(textStart, start));
			sb.append("(x=1)");
			skipping++;
			in_INLINE_EQ = true;
		    }
		}
		break;
	    case BEGIN_DESCR:
	    case BEGIN_VERSE:
	    case END_DESCR:
	    case END_VERSE:
		if (skipping == 0) {
		    sb.append(text.substring(textStart, start));
		    textStart = end;
		}
		break;
	    case INPUT:
	    case INCLUDE:
	    case INCLUDE_ONLY:
	    case INCLUDEGRAPHICS:
		skipCBRACE = true;
		if (skipping == 0) {
		    sb.append(text.substring(textStart, start));
		    sb.append("(graphics)");
		    int anyind = end;
		    char ch = text.charAt(anyind);
		    while (ch != '}') {
			ch = text.charAt(++anyind);
		    }
		    textStart = anyind+1;
		}
		break;
	    case BEGIN_ANY:
		skipCBRACE = true;
		{
		    if (skipping == 0) {
			sb.append(text.substring(textStart, start));
		    }
		    StringBuilder sb2 = new StringBuilder();
		    int anyind = end;
		    char ch = text.charAt(anyind);
		    while (ch != '}') {
			sb2.append(ch);
			ch = text.charAt(++anyind);
		    }
		    String cmd = sb2.toString();
		    Directive directive = directives.get(PatternType.BEGIN_ANY,
							 cmd);
		    directiveStack.push(directive);
		    if (skipping == 0) {
			textStart = anyind+1;
		    }
		    // anydepth++;
		    if (directive.replace) {
			sb.append(directive.replacement);
			skipping++;
		    }
		}
		break;
	    case END_ANY:
		skipCBRACE = true;
		{
		    if (skipping == 0) {
			sb.append(text.substring(textStart, start));
		    }
		    Directive directive = directiveStack.pop();
		    if (directive.replace) {
			skipping--;
		    }
		    if (skipping == 0) {
			int anyind = end;
			char ch = text.charAt(anyind);
			while (ch != '}') {
			    ch = text.charAt(++anyind);
			}
			textStart = anyind+1;
		    }
		}
		/*
		anydepth--;
		skipping--;
		if (anydepth == 0) {
		    int anyind = end;
		    char ch = text.charAt(anyind);
		    while (ch != '}') {
			ch = text.charAt(++anyind);
		    }
		    textStart = anyind+1;
		}
		*/
		break;
	    case COMMENT:
		if (skipping == 0) {
		    sb.append(text.substring(textStart, start));
		}
		skipping++;
		in_COMMENT = true;
		startOfComment = end;
		break;
	    case NEWCOMMAND:
	    case RENEWCOMMAND:
	    case PROVIDECOMMAND:
		{
		    int tlen = text.length();
		    boolean first = true;
		    boolean checkBracket = true;
		    int bdepth = 0;
		    while (end < tlen) {
			char ch = text.charAt(end);
			if (first) {
			    if (ch == '}') {
				first = false;
				end++;
				continue;
			    } else {
				end++;
				continue;
			    }
			}
			if (checkBracket && ch == '[') {
			    while (end < tlen && ch != ']') {
				end++;
				ch = text.charAt(end);
			    }
			    if (end == tlen) break;
			    end++;
			    checkBracket = false;
			    continue;
			}
			if (ch == '\\') {
			    // prevents processing \{ or \}
			    end += 2;
			    continue;
			}
			if (ch == '{') bdepth++;
			else if (ch == '}') bdepth--;
			end++;
			if (bdepth == 0) {
			    break;
			}
		    }
		    if (skipping == 0) {
			sb.append(text.substring(textStart, start));
		    }
		    textStart = end;
		    lastend = end;
		}
		break;
	    case NEWENVIRONMENT:
	    case RENEWENVIRONMENT:
		{
		    int tlen = text.length();
		    boolean first = true;
		    int bdepth = 0;
		    int count = 0;
		    while (end < tlen) {
			char ch = text.charAt(end);
			if (first) {
			    if (ch == '}') {
				first = false;
				end++;
				continue;
			    } else {
				end++;
				continue;
			    }
			}
			if (ch == '\\') {
			    // prevents processing \{ or \}
			    end++;
			    ch = text.charAt(end);
			    if (ch == '{' || ch == '}') {
				end++;
			    }
			    continue;
			}
			if (ch == '{') bdepth++;
			else if (ch == '}') bdepth--;
			end++;
			if (bdepth == 0) {
			    count++;
			    if (count == 2) {
				break;
			    }
			}
		    }
		    if (skipping == 0) {
			sb.append(text.substring(textStart, start));
		    }
		    textStart = end;
		    lastend = end;
		}
		break;
	    case SLASH_ANY:
		{
		    int tlen = text.length();
		    if (end < tlen) {
			char ech = (end < tlen)? text.charAt(end): '\0';
			if (!Character.isLetter(ech)
			    && !Character.isWhitespace(ech)) {
			    if (skipping == 0) {
				sb.append(text.substring(textStart, start));
			    }
			    textStart = end+1;
			    break;
			}
		    }
		    if (end < tlen) {
			String cmd = null;
			while (end < tlen) {
			    char ch = text.charAt(end);
			    if (Character.isLetter(ch)) {
				end++;
			    } else {
				cmd = text.substring(start, end);
				if (ch == ' ') end++;
				break;
			    }
			}
			if (skipping == 0) {
			    sb.append(text.substring(textStart, start));
			    Directive directive = directives
				.get(PatternType.SLASH_ANY, cmd);
			    if (directive.replace && text.charAt(end) == '{') {
				sb.append(directive.replacement);
				skipToDepth = depth;
				skipping++;
			    } else {
				// lastend = end;
			    }
			    textStart = end;
			}
		    }
		}
	    }
	}
	if (endDocSeen) {
	    return result;
	}
	if (!in_COMMENT) {
	    sb.append(text.substring(textStart));
	}
	if (scandepth == 0) {
	    offsetMap.put(base + sb.length()-1, lineno);
	}
	result.add(sb.toString());
	return result;
    }

    private static boolean useLocalWords = true;

    private static void
	displayServerResponse(Object obj, String data, String fname, int base)
    {
	if (obj instanceof JSObject) {
	    JSObject object = (JSObject) obj;
	    JSArray array = (JSArray)object.get("matches");
	    for (Object ob: array) {
		object = (JSObject) ob;
		int off = object.get("offset", Integer.class);
		int len = object.get("length", Integer.class);
		String err = (len < 17)? data.substring(off, off+len):
		    data.substring(off, off+16) + "...";
		String msg = object.get("message", String.class);
		if (useLocalWords) {
		    String issueType = object.get("rule", JSObject.class)
			.get("issueType", String.class);
		    if (issueType.equals("misspelling")) {
			String word = data.substring(off, off+len);
			if (localWords.contains(word)) continue;
		    }
		}
		if (len > 0 && msg.endsWith(".")) {
		    msg = msg.substring(0, msg.length()-1);
		}
		System.out.format("\"%s\", line %d: %s\n",
				  fname,
				  (Integer)offsetMap.ceilingEntry(base+off)
				  .getValue(),
				  msg
				  + ((len > 0)? ": " + err: ""));
	    }
	}
    }

    static java.util.regex.Pattern verbatimPattern = java.util.regex.Pattern
	.compile("([\\\\]end)(\\p{Blank}+)([{]verbatim[*]?[}])");

    static java.util.regex.Pattern inputPattern = java.util.regex.Pattern
	.compile("([\\\\][A-Za-z][A-Za-z0-9]*)(\\p{Blank}+)([{])");

    static java.util.regex.Pattern  inputPattern2 = java.util.regex.Pattern
		.compile("(\n)(\\p{Blank}+)(\\p{Print})");


    public static void main(String argv[]) throws Exception {

	String host = "localhost";
	int port = 8081;
	String version = "v2";
	String proto = "http";
	String urlString = null;
	boolean textmode = false;
	boolean skipMode = true;
	boolean odtmode = false;
	boolean raw = false;
	boolean justPrint = false;
	boolean prompt = false;
	boolean listLocalWords = false;
	boolean listOffsetMap = false;
	int partShown = -1;
	boolean notesShown = false;
	int argind = 0;

	while (argind < argv.length) {
	    if (argv[argind].equals("--")) {
		argind++;
		break;
	    } else if (argv[argind].equals("--text")) {
		textmode = true;
		odtmode = false;
	    } else if (argv[argind].equals("--odt")) {
		odtmode = true;
		textmode = false;
	    } else if (argv[argind].equals("--tex")) {
		odtmode = false;
		textmode = false;
	    } else if (argv[argind].equals("--itex")) {
		odtmode = false;
		textmode = false;
		skipMode = false;
	    } else if (argv[argind].equals("--host")
		       || argv[argind].equals("-h")) {
		argind++;
		if (argind == argv.length) {
		    System.err.println("ltgcheck: missing host name");
		    System.exit(1);
		}
		host = argv[argind];
	    } else if (argv[argind].equals("--port")
		       || argv[argind].equals("-p")) {
		argind++;
		if (argind == argv.length) {
		    System.err.println("ltgcheck: missing host name");
		    System.exit(1);
		}
		try {
		    port = Integer.parseInt(argv[argind]);
		    if (port <= 0 || port > 65535) {
			System.err.println("ltgcheck: bad port");
			System.exit(1);
		    }
		} catch (Exception e) {
		    System.err.println("ltgcheck: bad port");
		    System.exit(1);
		}
	    } else if (argv[argind].equals("--version")) {
		argind++;
		if (argind == argv.length) {
		    System.err.println("ltgcheck: missing version");
		    System.exit(1);
		}
		version = argv[argind];
	    } else if (argv[argind].equals("--https")) {
		proto = "https";
	    } else if (argv[argind].equals("--url")) {
		argind++;
		if (argind == argv.length) {
		    System.err.println("ltgcheck: missing URL");
		    System.exit(1);
		}
		urlString = argv[argind];
	    } else if (argv[argind].equals("--chapter")) {
		argind++;
		if (argind == argv.length) {
		    System.err.println("ltgcheck: missing host name");
		    System.exit(1);
		}
		try {
		    partShown = Integer.parseInt(argv[argind]);
		    if (partShown < 0) {
			throw new Exception("negative chapter");
		    }
		} catch (Exception e) {
		    System.err.println("ltgcheck: the argument to --chapter"
				       + " must be a non-negative integer");
		    System.exit(1);
		}
	    } else if (argv[argind].equals("--notes")) {
		notesShown = true;
	    } else if (argv[argind].equals("--print")) {
		justPrint = true;
	    } else if(argv[argind].equals("--raw")) {
		raw = true;	// used only with odt
	    } else if (argv[argind].equals("--prompt")) {
		prompt = true;
	    } else if (argv[argind].equals("--ignoreLocalWords")) {
		    useLocalWords = false;
	    } else if (argv[argind].equals("--listLocalWords")) {
		listLocalWords = true;
	    } else if (argv[argind].equals("--listOffsetMap")) {
		// for debugging, so not documented
		listOffsetMap = true;
	    } else if (argv[argind].equals("-?")
		       || argv[argind].equals("--help")) {
		System.out.println("ltgcheck OPTIONS [FILE]");
		System.out.println("    --httts (use HTTPS instead of HTTP)");
		System.out.println("    --host HOST (host name)");
		System.out.println("    --port PORT (TCP port)");
		System.out.println("    --version VERSION"
				   + " (LanguageTool version)");
		System.out.println("    --url URL (LanguageTool URL)");
		System.out.println("    --odt (input is open document text");
		System.out.println("    --print (print after normalizing)");
		System.out.println("    --prompt (ask before overwriting)");
		System.out.println("    --raw (just print ODT content)");
		System.out.println("    --tex (input is  LaTeX)");
		System.out.println("    --text (input is a plain text)");
		System.out.println("    --help (show this message)");
		System.out.println("shortcuts: -h -> --host, -p -> --port, "
				   + "-? -> --help");
		System.exit(0);
	    } else {
		break;
	    }
	    argind++;
	}

	// URL url = new URL("http://" + host + ":" + port +"/v2/check");
	URL url = (urlString == null)?
	    new URL(proto + "://" + host + ":" + port +"/"
		    + version + "/check"):
	    new URL(urlString);

	int findex = (argind == argv.length)? -1: argind;


	String filename = (findex >= 0)? argv[findex]: null;

	if (filename != null) {
	    if (filename.endsWith(".tex")) {
		if (textmode || odtmode) {
		    System.err
			.println("ltgcheck: --text or --odt not allowed for "
				 + ".tex files");
		    System.exit(1);
		}
	    } else if (filename.endsWith(".odt")) {
		if (textmode) {
		    System.err
			.println("ltgcheck: --text not allowed for .odt files");
		    System.exit(1);
		}
		textmode = true; odtmode = false;
		String textfile = filename.substring(0, filename.length()-3)
		    + "txt";
		ZipFile zf = new ZipFile(filename);
		ZipEntry entry = zf.getEntry("content.xml");
		InputStream is = zf.getInputStream(entry);
		String text = new String(is.readAllBytes(), "UTF-8");
		if (raw) {
		    System.out.println(text);
		    System.exit(0);
		}
		text = text
		    .replaceAll("<text:p( [^>]*>|>)", "\n")
		    .replaceAll("</text:p>", "\n")
		    .replaceAll("<text:h( [^>]*>|/?>)", "\n")
		    .replaceAll("</text:h>", "\n")
		    .replaceAll("<text:s( [^>]*>|/?>)", " ")
		    .replaceAll("(<[^>]*>)", "")
		    .replaceAll("([A-Za-z][A-Za-z][.])([ \t]+)", "$1\n")
		    .replaceAll("\n\n+", "\n\n");
		if (justPrint) {
		    System.out.println(text);
		    System.exit(0);
		}
		if (prompt) {
		    Console cons = System.console();
		    File tf = new File(textfile);
		    if (cons != null && tf.exists()) {
			String answer = cons.readLine("Overwrite %s? (yes/no)",
						      textfile);
			boolean ok =
			    (answer == null || answer.trim().length() == 0
			     || answer.equals("yes"));
			if (ok == false) System.exit(0);
		    }
		}
		PrintStream ps = new PrintStream(textfile, "UTF-8");
		ps.println(text);
		ps.close();
		filename = textfile;
	     } else if (filename.endsWith(".txt")) {
		if (odtmode) {
		    System.err
			.println("ltgcheck:--odt not allowed for .txt files");
		    System.exit(1);
		}
		textmode = true;
	     } else {
		if (odtmode) {
		    System.err
			.println("ltgcheck: --odt not allowed for file type");
		    System.exit(1);
		}
		textmode = true;
	     }
	 } else {
	     if (odtmode) {
		    System.err
			.println("ltgcheck: --odt not allowed for for stdin");
		    System.exit(1);
	     }
	 }

	 if (raw) {
	     System.out.println("ltgtest: --raw not allowed "
			      + "(open document format only)");
	     System.exit(1);
	 }

	 String fname = ((findex == -1)? "[stdin]": filename);


	 InputStream input = null;
	 String text = null;
	 try {
	     input = (findex >= 0)? new FileInputStream(filename):
		System.in;
	     if (textmode) {
		text =  new String(input.readAllBytes(), "UTF-8");
	     } else {
		Matcher m = verbatimPattern
		    .matcher(new String(input.readAllBytes(), "UTF-8"));
		text = inputPattern.matcher(m.replaceAll("$1\u2423$3"))
		    .replaceAll("$1$3");
		text = inputPattern2.matcher(text).replaceAll("$1$3")
		    .replace("\u2423"," ");
		/*
		Matcher m = inputPattern
		    .matcher(new String(input.readAllBytes(), "UTF-8"));
		text = inputPattern2.matcher(m.replaceAll("$1$3"))
		    .replaceAll("$1$3");
		*/
	     }
	 } catch (Exception e) {
	     System.err.println(e.getMessage());
	     System.exit(1);
	 }


	/*
	System.out.println("----");
	System.out.println(text);
	System.out.println("---- (len = " + text.length() + ")");
	*/

	ArrayList<String> data = textmode? scan(textPatterns, 1, false, text):
	    scan(latexPatterns, 1, skipMode, text);

	if (listOffsetMap) {
	    System.out.println("offset map:");
	    for (Map.Entry<Integer,Integer> entry: offsetMap.entrySet()) {
		System.out.format("    %d -> %d\n",
				  entry.getKey(), entry.getValue());
	    }
	    System.exit(0);
	}
	
	if (listLocalWords) {
	    System.out.println("--- LOCAL WORDS ---");
	    for (String s: localWords) {
		System.out.println(s);
	    }
	    System.exit(0);
	}

	/*
	for (String s: data) {
	    System.out.print(s);
	}
	System.out.println();
	*/
	int part = 0;
	int base = 0;
	for (String datum: data) {
	    if (notesShown && partShown < 0) continue;
	    if (part < partShown) {
		base += datum.length();
		part++;
		continue;
	    }
	    if (partShown >= 0 && partShown < part) break;
	    part++;
	    if (justPrint) {
		System.out.print(datum);
	    } else {
		if (data.size() == 1) {
		    System.out.println("*** PROCESSING");
		} else {
		    if (part == 1) {
			System.out.println("*** PROCESSING PREFACE");
		    } else {
			System.out.println("*** PROCESSING CHAPTER "
					   + (part - 1));
		    }
		    // System.out.println("*** PROCESSING PART " + part);
		}
		Object obj = queryServer(url, datum);
		if (obj != null) {
		    displayServerResponse(obj, datum, fname, base);
		} else {
		    System.err.println("ltgcheck: could not process data");
		    System.exit(1);
		}
		base += datum.length();
	    }
	}
	// If we limit output to a specific chapter and the user
	// has not used the --notes option, just exit.
	if (partShown >= 0 && notesShown == false) {
	    System.exit(0);
	}

	if (noteList.size() > 0) {
	    if (justPrint) {
		System.out.println();
		System.out.println("-- FOOTNOTES AND CAPTIONS --");
	    } else {
		System.out.println("-- processing footnotes and captions --");
	    }
	}

	/*
	TreeSet<Map.Entry<Integer,String>> entries = new
	    TreeSet<>((e1, e2) -> {
		int line1 = e1.getKey();
		int line2 = e2.getKey();
		String s1 = e1.getValue();
		String s2 = e2.getValue();
		int len1 = s1.length();
		int len2 = s1.length();
		if (line1 != line2) return line1 - line2;
		if (len1 != len2) return len2 - len1;
		return s1.compareTo(s2);
	    });
	entries.addAll(footnoteMap.entrySet());
	entries.addAll(captionMap.entrySet());
	*/

	noteList.sort((e1, e2) -> {
			 int line1 = e1.getKey();
			 int line2 = e2.getKey();
			 String s1 = e1.getValue();
			 String s2 = e2.getValue();
			 int len1 = s1.length();
			 int len2 = s1.length();
			 if (line1 != line2) return line1 - line2;
			 if (len1 != len2) return len2 - len1;
			 return s1.compareTo(s2);});

	for (NoteEntry entry: noteList
		 .toArray(new NoteEntry[noteList.size()])) {
	    if (justPrint) {
		System.out.println(entry.getValue());
	    } else { 
		int startingLineNo = entry.getKey();
		String txt = entry.getValue();
		// System.out.println("txt = " + txt);
		data = scan(latexPatterns, startingLineNo, false, txt);
		Object obj = queryServer(url, data.get(0));
		if (obj != null) {
		    displayServerResponse(obj, data.get(0), fname, 0);
		} else {
		    System.err.println("could not process data");
		    System.exit(1);
		}
	    }
	}
	System.exit(0);
    }
}
