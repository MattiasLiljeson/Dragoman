package com.liljeson.mattias.fries.interpretator;

public class RDITestData {
	static final String[] TST0 = {
			"###PROGRAM###",
			"    1",
			"##BLOCK##",
			"  0 -1  0  0    3",
			"#DEKLARATIONER#",
			"#KOD#",
			"    2    7    Line#",
			"    3    7    Line#",
			"    2    1    end",
			"##BLOCKSLUT##",
			"###PROGRAMSLUT###" };

	static final String[] TST1 = {
			"###PROGRAM###",
			"    1",
			"##BLOCK##",
			"  0 -1  1  1   10",
			"#DEKLARATIONER#",
			"   19    1    0    0    0    0    0  **   #   I",
			"#KOD#",
			"    2    7    Line#",
			"    3    7    Line#",
			"    4    7    Line#",
			"    5    7    Line#",
			"   19    1    I",
			"    6    5    :=",
			"    2    2    2",
			"    1    5    ;",
			"# BREAKPOINT 19 -1 I",
			"    6    7    Line#",
			"    2    1    end",
			"##BLOCKSLUT##",
			"###PROGRAMSLUT###",
	};

	static final String[] TST2 = {
			"###PROGRAM###",
			"    1",
			"##BLOCK##",
			"  0 -1  2  2   22",
			"#DEKLARATIONER#",
			"   19    1    0    0    0    0    0  **   #   I",
			"   20    1    0    0    0    0    1  **   #   K",
			"#KOD#",
			"    2    7    Line#",
			"    3    7    Line#",
			"    4    7    Line#",
			"    5    7    Line#",
			"   19    1    I",
			"    6    5    :=",
			"    2    2    2",
			"    1    5    ;",
			"# BREAKPOINT 19 -1 I",
			"    6    7    Line#",
			"   20    1    K",
			"    6    5    :=",
			"    3    2    3",
			"    1    5    ;",
			"# BREAKPOINT 20 -1 K",
			"    7    7    Line#",
			"   19    1    I",
			"    6    5    :=",
			"   19    1    I",
			"   13    5    +",
			"   20    1    K",
			"    1    5    ;",
			"# BREAKPOINT 19 -1 newI",
			"    8    7    Line#",
			"    2    1    end",
			"##BLOCKSLUT##",
			"###PROGRAMSLUT###",
	};

	static final String[] TST3 = {
			"###PROGRAM###",
			"    1",
			"##BLOCK##",
			"  0 -1  2  2   27",
			"#DEKLARATIONER#",
			"   19    1    0    0    0    0    0  **   #   I",
			"   20    1    0    0    0    0    1  **   #   K",
			"#KOD#",
			"    2    7    Line#",
			"    3    7    Line#",
			"    4    7    Line#",
			"    5    7    Line#",
			"    6    7    Line#",
			"   19    1    I",
			"    6    5    :=",
			"    2    2    2",
			"    1    5    ;",
			"    7    7    Line#",
			"   20    1    K",
			"    6    5    :=",
			"    3    2    3",
			"    1    5    ;",
			"# BREAKPOINT 19 -1 I",
			"# BREAKPOINT 20 -1 K",
			"    8    7    Line#",
			"   19    1    I",
			"    6    5    :=",
			"   19    1    I",
			"   13    5    +",
			"   20    1    K",
			"   15    5    *",
			"   19    1    I",
			"   14    5    -",
			"   20    1    K",
			"    1    5    ;",
			"# BREAKPOINT 19 -1 newI",
			"    9    7    Line#",
			"    2    1    end",
			"##BLOCKSLUT##",
			"###PROGRAMSLUT###"
	};

	static final String[] TST4 = {
			"###PROGRAM###",
			"    2",
			"##BLOCK##",
			"  0 -1  2  2   25",
			"#DEKLARATIONER#",
			"   19    1    0    0    0    0    0  **   #   I",
			"   20    1    0    0    0    0    1  **   #   K",
			"#KOD#",
			"    2    7    Line#",
			"    3    7    Line#",
			"    4    7    Line#",
			"    5    7    Line#",
			"   19    1    I",
			"    6    5    :=",
			"    2    2    2",
			"    1    5    ;",
			"    6    7    Line#",
			"   20    1    K",
			"    6    5    :=",
			"    3    2    3",
			"    1    5    ;",
			"# BREAKPOINT 19 -1 I",
			"# BREAKPOINT 20 -1 K",
			"    7    7    Line#",
			"    8    7    Line#",
			"    1   10    Callblock",
			"   12    7    Line#",
			"   19    1    I",
			"    6    5    :=",
			"   20    1    K",
			"   15    5    *",
			"   19    1    I",
			"    1    5    ;",
			"# BREAKPOINT 19 -1 finalI",
			"# BREAKPOINT 20 -1 finalK",
			"   13    7    Line#",
			"    2    1    end",
			"##BLOCKSLUT##",
			"##BLOCK##",
			"  1  0  1  1   14",
			"#DEKLARATIONER#",
			"   19    1    0    0    0    0    0  **   #   I",
			"#KOD#",
			"    9    7    Line#",
			"   19    1    I",
			"    6    5    :=",
			"    4    2    4",
			"    1    5    ;",
			"# BREAKPOINT 19 -1 settingOfLocalI",
			"   10    7    Line#",
			"   20    1    K",
			"    6    5    :=",
			"   19    1    I",
			"   13    5    +",
			"   20    1    K",
			"    1    5    ;",
			"# BREAKPOINT 20 -1 kHasBeenSetInInnerBlock",
			"   11    7    Line#",
			"    2    1    end",
			"##BLOCKSLUT##",
			"###PROGRAMSLUT###",
	};

	static final String[] TST5 = {
			"###PROGRAM###",
			"    2",
			"##BLOCK##",
			"  0 -1  1  2   27",
			"#DEKLARATIONER#",
			"   19    1    2    1    1    0    0  **   #   F",
			"   21    1    0    0    0    0    0  **   #   I",
			"#KOD#",
			"    2    7    Line#",
			"    3    7    Line#",
			"    4    7    Line#",
			"    5    7    Line#",
			"   21    1    I",
			"    6    5    :=",
			"    3    2    3",
			"    1    5    ;",
			"# BREAKPOINT 21 -1 IHasBeenSet",
			"    6    7    Line#",
			"   21    1    I",
			"    6    5    :=",
			"   21    1    I",
			"   13    5    +",
			"   19    1    F",
			"    2    5    (",
			"    2    2    2",
			"    3    5    )",
			"   15    5    *",
			"   21    1    I",
			"   13    5    +",
			"   19    1    F",
			"    2    5    (",
			"   21    1    I",
			"    3    5    )",
			"    1    5    ;",
			"# BREAKPOINT 21 -1 IHasBeenSetASecondTime",
			"    7    7    Line#",
			"    2    1    end",
			"##BLOCKSLUT##",
			"##BLOCK##",
			"  1  0  2  2    6",
			"#DEKLARATIONER#",
			"   19    1    3    0    0    0    0  **   #   F",
			"   20    1    0    0    0    0    1  **   #   X",
			"#KOD#",
			"   19    1    F",
			"    6    5    :=",
			"   20    1    X",
			"   13    5    +",
			"    1    2    1",
			"    1    5    ;",
			"# BREAKPOINT 19 -1 FuncvalHasBeenSet",
			"##BLOCKSLUT##",
			"###PROGRAMSLUT###"
	};

	static final String[] TST6 = {
			"###PROGRAM###",
			"    2",
			"##BLOCK##",
			"  0 -1  1  2   27",
			"#DEKLARATIONER#",
			"   19    1    2    1    1    0    0  **   #   F",
			"   21    1    0    0    0    0    0  **   #   I",
			"#KOD#",
			"    2    7    Line#",
			"    3    7    Line#",
			"    7    7    Line#",
			"    8    7    Line#",
			"   21    1    I",
			"    6    5    :=",
			"    3    2    3",
			"    1    5    ;",
			"    9    7    Line#",
			"   21    1    I",
			"    6    5    :=",
			"   21    1    I",
			"   13    5    +",
			"   19    1    F",
			"    2    5    (",
			"    2    2    2",
			"    3    5    )",
			"   15    5    *",
			"   21    1    I",
			"   13    5    +",
			"   19    1    F",
			"    2    5    (",
			"   21    1    I",
			"    3    5    )",
			"    1    5    ;",
			"# BREAKPOINT 21 -1 afterRecursionI",
			"   10    7    Line#",
			"    2    1    end",
			"##BLOCKSLUT##",
			"##BLOCK##",
			"  1  0  2  2   24",
			"#DEKLARATIONER#",
			"   19    1    3    0    0    0    0  **   #   F",
			"   20    1    0    0    0    0    1  **   #   X",
			"#KOD#",
			"    4    7    Line#",
			"    5    7    Line#",
			"   10    1    if",
			"   20    1    X",
			"   11    5    >",
			"    0    2    0",
			"   11    1    then",
			"   19    1    F",
			"    6    5    :=",
			"   20    1    X",
			"   13    5    +",
			"   19    1    F",
			"    2    5    (",
			"   20    1    X",
			"   14    5    -",
			"    1    2    1",
			"    3    5    )",
			"   12    1    else",
			"   19    1    F",
			"    6    5    :=",
			"    0    2    0",
			"    1    5    ;",
			"    6    7    Line#",
			"    2    1    end",
			"##BLOCKSLUT##",
			"###PROGRAMSLUT###"
	};

	static final String[] TST7 = {
			"###PROGRAM###",
			"    4",
			"##BLOCK##",
			"  0 -1  1  2   30",
			"#DEKLARATIONER#",
			"   19    1    2    1    1    0    0  **   #   F",
			"   22    1    0    0    0    0    0  **   #   I",
			"#KOD#",
			"    2    7    Line#",
			"    3    7    Line#",
			"   13    7    Line#",
			"   14    7    Line#",
			"   22    1    I",
			"    6    5    :=",
			"    3    2    3",
			"    1    5    ;",
			"   15    7    Line#",
			"   16    7    Line#",
			"    3   10    Callblock",
			"   20    7    Line#",
			"   22    1    I",
			"    6    5    :=",
			"   22    1    I",
			"   13    5    +",
			"   19    1    F",
			"    2    5    (",
			"    2    2    2",
			"    3    5    )",
			"   15    5    *",
			"   22    1    I",
			"   13    5    +",
			"   19    1    F",
			"    2    5    (",
			"   22    1    I",
			"    3    5    )",
			"    1    5    ;",
			"# BREAKPOINT 22 -1 afterRecursionI",
			"   21    7    Line#",
			"    2    1    end",
			"##BLOCKSLUT##",
			"##BLOCK##",
			"  1  0  2  2   24",
			"#DEKLARATIONER#",
			"   19    1    3    0    0    0    0  **   #   F",
			"   20    1    0    0    0    0    1  **   #   X",
			"#KOD#",
			"    4    7    Line#",
			"    5    7    Line#",
			"   10    1    if",
			"   20    1    X",
			"   11    5    >",
			"    0    2    0",
			"   11    1    then",
			"   19    1    F",
			"    6    5    :=",
			"   20    1    X",
			"   13    5    +",
			"   19    1    F",
			"    2    5    (",
			"   20    1    X",
			"   14    5    -",
			"    1    2    1",
			"    3    5    )",
			"    6    7    Line#",
			"   12    1    else",
			"    7    7    Line#",
			"    8    7    Line#",
			"    2   10    Callblock",
			"   12    7    Line#",
			"    2    1    end",
			"##BLOCKSLUT##",
			"##BLOCK##",
			"  2  1  1  1   14",
			"#DEKLARATIONER#",
			"   21    1    0    0    0    0    0  **   #   K",
			"#KOD#",
			"    9    7    Line#",
			"   21    1    K",
			"    6    5    :=",
			"   22    1    I",
			"   14    5    -",
			"    1    2    1",
			"    1    5    ;",
			"   10    7    Line#",
			"   19    1    F",
			"    6    5    :=",
			"   21    1    K",
			"    1    5    ;",
			"   11    7    Line#",
			"    2    1    end",
			"##BLOCKSLUT##",
			"##BLOCK##",
			"  3  0  1  1   21",
			"#DEKLARATIONER#",
			"   21    1    0    0    0    0    0  **   #   K",
			"#KOD#",
			"   17    7    Line#",
			"   21    1    K",
			"    6    5    :=",
			"   19    1    F",
			"    2    5    (",
			"   22    1    I",
			"   13    5    +",
			"    1    2    1",
			"    3    5    )",
			"   15    5    *",
			"    2    2    2",
			"    1    5    ;",
			"   18    7    Line#",
			"   22    1    I",
			"    6    5    :=",
			"   21    1    K",
			"   13    5    +",
			"    1    2    1",
			"    1    5    ;",
			"   19    7    Line#",
			"    2    1    end",
			"##BLOCKSLUT##",
			"###PROGRAMSLUT###"
	};

	static final String[] TST8 = {
			"###PROGRAM###",
			"    1",
			"##BLOCK##",
			"  0 -1  2  2   48",
			"#DEKLARATIONER#",
			"   19    1    0    0    0    0    0  **   #   I",
			"   20    1    1    1    0    0    1  **    1  2  6  A",
			"#KOD#",
			"    2    7    Line#",
			"    3    7    Line#",
			"    4    7    Line#",
			"   19    1    I",
			"    6    5    :=",
			"    4    2    4",
			"    1    5    ;",
			"    5    7    Line#",
			"   20    1    A",
			"    2    5    (",
			"    3    2    3",
			"    3    5    )",
			"    6    5    :=",
			"    5    2    5",
			"    1    5    ;",
			"    6    7    Line#",
			"   20    1    A",
			"    2    5    (",
			"   19    1    I",
			"    3    5    )",
			"    6    5    :=",
			"   19    1    I",
			"   13    5    +",
			"   20    1    A",
			"    2    5    (",
			"    3    2    3",
			"    3    5    )",
			"    1    5    ;",
			"    7    7    Line#",
			"   20    1    A",
			"    2    5    (",
			"   19    1    I",
			"   13    5    +",
			"    1    2    1",
			"    3    5    )",
			"    6    5    :=",
			"   20    1    A",
			"    2    5    (",
			"    4    2    4",
			"    3    5    )",
			"   15    5    *",
			"   20    1    A",
			"    2    5    (",
			"    3    2    3",
			"    3    5    )",
			"    1    5    ;",
			"# BREAKPOINT 20 3 afterRecursionI",
			"# BREAKPOINT 20 4 afterRecursionI",
			"# BREAKPOINT 20 5 afterRecursionI",
			"    8    7    Line#",
			"    2    1    end",
			"##BLOCKSLUT##",
			"###PROGRAMSLUT###"
	};

	static final String[] TST9 = {
			"###PROGRAM###",
			"    1",
			"##BLOCK##",
			"  0 -1  1  1   44",
			"#DEKLARATIONER#",
			"   19    1    0    0    0    0    0  **   #   I",
			"#KOD#",
			"    2    7    Line#",
			"    3    7    Line#",
			"   19    1    I",
			"    6    5    :=",
			"    5    2    5",
			"    1    5    ;",
			"    4    7    Line#",
			"   20    1    write",
			"    2    5    (",
			"   19    1    I",
			"    3    5    )",
			"    1    5    ;",
			"    5    7    Line#",
			"   21    1    writln",
			"    1    5    ;",
			"    6    7    Line#",
			"   19    1    I",
			"    6    5    :=",
			"   22    1    readint",
			"    1    5    ;",
			"    7    7    Line#",
			"   20    1    write",
			"    2    5    (",
			"   19    1    I",
			"    3    5    )",
			"    1    5    ;",
			"    8    7    Line#",
			"   21    1    writln",
			"    1    5    ;",
			"    9    7    Line#",
			"   20    1    write",
			"    2    5    (",
			"   19    1    I",
			"   13    5    +",
			"   22    1    readint",
			"   14    5    -",
			"    2    2    2",
			"    3    5    )",
			"    1    5    ;",
			"   10    7    Line#",
			"   21    1    writln",
			"    1    5    ;",
			"   11    7    Line#",
			"    2    1    end",
			"##BLOCKSLUT##",
			"###PROGRAMSLUT###"
	};

	static final String[] DELUXE = {
			"###PROGRAM###",
			"3",
			"##BLOCK##",
			"0 -1 5 6 42",
			"#DEKLARATIONER#",
			"19 1 0 0 0 0 0 ** # I",
			"20 1 0 0 0 0 1 ** # K",
			"21 2 0 0 0 0 2 ** # X",
			"22 3 0 0 0 0 3 ** # H",
			"23 1 1 1 0 0 4 ** 1 2 9 A",
			"24 1 2 2 1 0 0 ** # Add",
			"#KOD#",
			"3 7 Line#",
			"4 7 Line#",
			"5 7 Line#",
			"6 7 Line#",
			"7 7 Line#",
			"19 1 I",
			"6 5 :=",
			"2 2 2",
			"1 5 ;",
			"8 7 Line#",
			"21 1 X",
			"6 5 :=",
			"5 3 4.5",
			"1 5 ;",
			"9 7 Line#",
			"20 1 K",
			"6 5 :=",
			"19 1 I",
			"13 5 +",
			"21 1 X",
			"1 5 ;",
			"10 7 Line#",
			"11 7 Line#",
			"2 10 Callblock",
			"14 7 Line#",
			"22 1 H",
			"6 5 :=",
			"0 4 Hej",
			"1 5 ;",
			"15 7 Line#",
			"26 1 write",
			"2 5 (",
			"24 1 Add",
			"2 5 (",
			"19 1 I",
			"5 5 ,",
			"20 1 K",
			"3 5 )",
			"3 5 )",
			"1 5 ;",
			"16 7 Line#",
			"2 1 end",
			"##BLOCKSLUT##",
			"##BLOCK##",
			"1 0 3 3 6",
			"#DEKLARATIONER#",
			"24 1 3 0 0 0 0 ** # Add",
			"21 1 0 0 0 0 1 ** # X",
			"25 1 0 0 0 0 2 ** # Y",
			"#KOD#",
			"24 1 Add",
			"6 5 :=",
			"21 1 X",
			"13 5 +",
			"25 1 Y",
			"1 5 ;",
			"##BLOCKSLUT##",
			"##BLOCK##",
			"2 0 1 1 7",
			"#DEKLARATIONER#",
			"19 1 0 0 0 0 0 ** # I",
			"#KOD#",
			"12 7 Line#",
			"19 1 I",
			"6 5 :=",
			"1 2 1",
			"1 5 ;",
			"13 7 Line#",
			"2 1 end",
			"##BLOCKSLUT##",
			"###PROGRAMSLUT###",
	};
}
