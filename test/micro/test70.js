var b = false;

function f() {y = 42;}

if (Math.random()) {
  f();
}

TAJS_dumpValue(y);
