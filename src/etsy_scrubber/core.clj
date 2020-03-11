(ns etsy-scrubber.core
  (:require [clojure.string :as str]
            [clj-http.client :as http])
  (:gen-class))

(defn format-link
  "Surround link with required clicked bot formatting for script"
  [link]
  (str "URL GOTO=" link (System/lineSeparator) 
       "TAG POS=2 TYPE=A ATTR=TXT:Follow<SP>Loading" (System/lineSeparator) 
       "WAIT SECONDS=2" (System/lineSeparator)))

(def macro-header (str "VERSION BUILD=1005 RECORDER=CR" (System/lineSeparator)))

(defn -main
  "Initialize scrapper with provided cookie and link"
  [& args]
  (if (< (count args) 1) 
    (doall
     (println "Need at least 1 argument to slurp HTML!")
     (System/exit 1)))
  
  (let [
        html (slurp (first args))
        found-links (re-seq #"http.*ref=shop_review" html)
        formatted-links (map format-link found-links)]
    (spit "new_script.iism" (str macro-header (apply str formatted-links)))
    ))
