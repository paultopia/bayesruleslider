
## What is this?

This is a small interactive Bayes Rule calculator in the context of test results.  I'm going to embed this in a tutorial to help illustrate the base rate fallacy for lawyers. Most of this repo has template code from the [reagent-frontend](https://github.com/reagent-project/reagent-frontend-template) template; what remains (i.e., my tiny amount of code) is under MIT License.  As, for that matter, is the reagent-frontend template. 

-----

## Building instructions from template

### Development mode
To start the Figwheel compiler, navigate to the project folder and run the following command in the terminal:

```
lein figwheel
```

Figwheel will automatically push cljs changes to the browser.
Once Figwheel starts up, you should be able to open the `public/index.html` page in the browser.

### REPL

The project is setup to start nREPL on port `7002` once Figwheel starts.
Once you connect to the nREPL, run `(cljs)` to switch to the ClojureScript REPL.

### Building for production

```
lein clean
lein package
```
