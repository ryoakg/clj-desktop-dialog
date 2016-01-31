(ns clj-desktop-dialog.core
  (:import (javax.swing JOptionPane)))

(defn- message-type [icon-type]
  ({:error     JOptionPane/ERROR_MESSAGE
    :info      JOptionPane/INFORMATION_MESSAGE
    :warn      JOptionPane/WARNING_MESSAGE
    :question  JOptionPane/QUESTION_MESSAGE}
   icon-type
   JOptionPane/PLAIN_MESSAGE))

(defn message
  "Shows a message dialog and returns immediately if `:non-block?' is truthy,
otherwise blocks until the OK-button on the dialog is pressed.

available `:icon's
  :error
  :info
  :warn
  :question

Usage example:
  (message \"Hello world!\" :title \"Hello\" :icon :info)"
  [msg & {:keys [title icon non-block?]}]
  (if non-block?
    (future (JOptionPane/showMessageDialog nil msg title (message-type icon)))
    (JOptionPane/showMessageDialog nil msg title (message-type icon))))

(defn- option-type [op]
  (or ({:yes-no         JOptionPane/YES_NO_OPTION
        :yes-no-cancel  JOptionPane/YES_NO_CANCEL_OPTION
        :ok-cancel      JOptionPane/OK_CANCEL_OPTION} op)
      (throw (ex-info "invalid option-type." {:option op}))))

(def ^:private option-selection
  {JOptionPane/YES_OPTION    true
   JOptionPane/NO_OPTION     false
   JOptionPane/CANCEL_OPTION nil})

(defn confirm
  "Shows a confirm dialog.

returns
  yes    → true
  no     → false
  cancel → nil

available `:option's
  :yes-no
  :yes-no-cancel
  :ok-cancel

available `:icon's
  :error
  :info
  :warn
  :question

Usage example:
  (confirm \"proceed?\" :yes-no)"
  [msg option & {:keys [title icon]}]
  (option-selection
   (JOptionPane/showConfirmDialog nil msg title (option-type option) (message-type icon))))

(defn select
  "Shows a select dialog.
Returns nil if canceled otherwise selected value.

available `:icon's
  :error
  :info
  :warn
  :question

Usage example:
  (select \"Choose your favorite fruit.\" [\"apple\" \"banana\" \"orange\"]
          :initial-selection \"banana\")"
  [msg candidates & {:keys [title icon initial-selection]}]
  (JOptionPane/showInputDialog nil msg title
                               (message-type icon) nil
                               (into-array candidates)
                               initial-selection))

(defn ^String input-text
  "Shows a text input dialog.
Returns nil if canceled otherwise the input text.

Usage example:
  (text-input :title \"Enter your name\" :initial-text \"John Doe\")"
  [& {:keys [^String title ^String initial-text]}]
  {:pre [(or (nil? initial-text)
             (string? initial-text))]}
  (JOptionPane/showInputDialog title initial-text))
