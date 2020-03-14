(defproject etsy-scrubber "1.1"
  :description "This is a small CLojure-based POC to try fetch firs page of Etsy reviews on a specific product and scrub the reviewer profile links"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [clj-http "0.7.7"]]
  :main ^:skip-aot etsy-scrubber.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
