(ns etsy-scrubber.core
  (:require [clojure.string :as str])
  (:gen-class))

(defn format-link
  "Surround link with required clicked bot formatting for script"
  [link]
  (str "URL GOTO=" link (System/lineSeparator)
       "TAG POS=2 TYPE=A ATTR=TXT:Follow<SP>Loading" (System/lineSeparator)
       "WAIT SECONDS=2" (System/lineSeparator)))

(def macro-header (str "VERSION BUILD=1005 RECORDER=CR" (System/lineSeparator)))

(defn fetch-links-page
  [base-url page-idx]
  (def reviews-url (str base-url "?ref=pagination&page=" page-idx))
  (println "Fetching reviews for " reviews-url)
  (let [html (slurp reviews-url)
        found-links (re-seq #"http.*ref=shop_review" html)]
    found-links))


(defn -main
  "Initialize scrapper with provided cookie and link"
  [& args]
  (if (< (count args) 1)
    (doall
     (println "Need shop url to slurp reviews!")
     (System/exit 1)))

  (def shop-url-base (first args))
  (def num-pages (or (Integer/parseInt (second args)) 25))
  (def shop-name (let [shop-name-bit (re-find #"/shop/.+/" shop-url-base)]
                   (subs shop-name-bit (count "/shop/") (dec (count shop-name-bit)))))

  (let [fetched-links-lists (map #(fetch-links-page shop-url-base %) (range 1 (inc num-pages)))
        all-links (set (flatten fetched-links-lists))
        formatted-links (map format-link all-links)]
    (println "Done fetching total of " (count all-links) " links!")
    (spit (str shop-name "_" num-pages "_pages.iism") (str macro-header (apply str formatted-links)))))
