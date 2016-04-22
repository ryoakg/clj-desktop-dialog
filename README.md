# clj-desktop-dialog

Simple dialog functions saving our memory from a Java mess.
This may be useful to working in REPL rather than run a task or a compiled JAR.

## Usage

    (require '[clj-desktop-dialog.core :as dialog])
    
    (dialog/message "Hello world!" :title "Hello" :icon :info)
    
    (dialog/confirm "proceed?" :yes-no)
    
    (dialog/select "Choose your favorite fruit." ["apple" "banana" "orange"]
                   :initial-selection "banana")
    
    (dialog/text-input :title "Enter your name" :initial-text "John Doe")

## License

Copyright © 2016 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
