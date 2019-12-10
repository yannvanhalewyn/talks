(ns talks.composition
  (:require
   #?(:clj  [casper.core :as casper :refer [defslide]]
      :cljs [casper.core :as casper :refer-macros [defslide]])
   [talks.brightin :as brightin]
   [clojure.string :as str]))

(defn- code [opts code]
  (let [{:keys [lang class]} (if (keyword? opts)
                               {:lang opts
                                :class "px-8"}
                               opts)]
    [:pre.text-left {:class (str class " text-left")}
     [:code {:class (str (name lang) "p-4 rounded-lg")}
      (str/trim (str code))]]))

(defslide title {}
  [:h1.title
   [:span.text-blue-600 "Composition"]
   " over "
   [:span.text-red-600 "Convention"]])

(defslide asylum {:hide-logo? true
                  :class "bg-black text-white"}
  [:video.absolute.inset-0.h-full.w-full
   {:controls true}
   [:source {:data-src "./img/asylum_snippet.mp4"}]])

(defslide code-slide {::casper/audiences #{:audience/ruby}}
  (code {:lang :lang/ruby :class "px-24"} "
class MyHairball
  include HairballOne
  include HairballTwo
end
"))

(defslide composition-1 {:casper/audience #{:audience/clojure}}
  [:div
   [:h1.title "Com • poser"]
   [:p "To lay near"]])

(defslide composition-2 {}
  [:div
   [:h1.title.mb-4 "Composition is powerful"]
   (code :lang/clojure "
(def can-drink?
  (comp #(>= % 21) :user/age))
")])

(defslide composition-3 {}
  [:div
   [:h1.title.mb-4 "Composition is powerful"]
   (code :lang/clojure "
(ns db.user)

(def accounts-report
  (comp account/summary user/all-accounts))
")])

(defslide thanks {}
  [:h1.title "Thanks!"])

(defn slides
  "Add here all slides you want to see in your presentation."
  []
  (casper/render-slides
   [title
    brightin/plug
    brightin/brightmotive-intro
    (casper/transition-group
     ["none" "none" "none"]
     [composition-1 composition-2 composition-3])
    code-slide
    asylum
    thanks]))
