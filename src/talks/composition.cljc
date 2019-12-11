(ns talks.composition
  (:require
   #?(:clj  [casper.core :as casper :refer [defslide]]
      :cljs [casper.core :as casper :refer-macros [defslide]])
   [talks.brightin :as brightin]
   [talks.layouts  :as layouts]
   [clojure.string :as str]))

(defn- code [opts code]
  (let [{:keys [lang class]} (if (keyword? opts)
                               {:lang opts}
                               opts)]
    [:pre.text-left.mx-auto {:class class}
     [:code.p-4 {:class (str (name lang) "p-4 rounded-lg")}
      (str/trim (str code))]]))

(defslide title {}
  [:h1.title
   [:span.text-blue-600 "Composition"]
   " over "
   [:span.text-red-600 "Convention"]])

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Composition

(defslide composition-1 {}
  [:div
   [:h1.title "Com • poser"]
   [:p "To lay near"]
   [:p "TODO: flush out slide"]])

(defslide xy-graph {}
  [:span "TODO x-y graph"])

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
(def accounts-report
  (comp account/summary user/all-accounts))
")
   [:aside.notes
    [:ul
     [:li "Notice how both functions don't know about eachother"]
     [:li "Notice that the `def` is the composition, and it is done "
      "via it's composable parts !!!! VERY IMPORTANT"]]]])

(def composition
  (casper/transition-group
   ["slide" "none" "slide"]
   [composition-1 xy-graph composition-2 composition-3]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Composition in music

(defslide musician {:slide/background-image "./img/guitar.jpg"
                    :slide/layout :layout/dark}
  [:h1.title (casper/colorize "I am also a " ["musician" :pink-600])])

(defslide asylum {:slide/layout :layout/none
                  :slide/class "bg-black text-white"}
  [:video.absolute.inset-0.h-full.w-full
   {:controls true}
   [:source {:data-src "./img/asylum_snippet.mp4"}]])

(def COMP_COLOR :blue-500)
(def ARR_COLOR :orange-500)

(defn- c-v-a-title []
  [:h1.title (casper/colorize ["Composer" COMP_COLOR] " vs "
                              ["Arranger" ARR_COLOR])])

(defslide composer-v-arranger {}
  [:<>
   (c-v-a-title)
   (casper/enumeration {:class "text-sm w-64"}
     (casper/colorize ["Composer" COMP_COLOR] " generates musical ideas")
     (casper/colorize ["Arranger" ARR_COLOR]  " assembles ideas into product")
     (casper/colorize "The " ["music    industry" :red-700] " acknowledges this")
     (casper/colorize "The " ["software industry" :red-700] " doesn't"))])

;; TODO Ruby example
(defslide c-v-a-code-example {}
  [:<>

   (c-v-a-title)
   (into
    [casper/enumeration {:class "flex flex-col justify-around h-48"}]
    (for [{:keys [title code-str]}
          [{:title ["Composer" COMP_COLOR]
            :code-str "
(defn composer []
  (random-sample (range all-musical-ideas)))
"}
           {:title ["Arranger" ARR_COLOR]
            :code-str "
(defn arranger [composition]
  (apply make-song composition))
"}]]
      ^{:key (first title)}
      [:div.flex.flex-wrap.items-center
       [:h1.title.min-w-48 (casper/colorize title)]
       (code {:lang :lang/clojure
              :class "flex-grow"}
             code-str)]))
   [:aside.notes
    "Again, notice that the composition is a list of many composable parts"]])

(def composition-in-music
  [musician
   asylum
   (casper/transition-group
    ["slide" "none" "slide"]
    [composer-v-arranger c-v-a-code-example])])

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Tower of composability

(defslide hairball {:slide/background-image "./img/its_a_trap.gif"
                    :slide/layout :layout/dark}
  (code {:lang :lang/ruby :class "px-24"} "
class MyHairball
  include HairballOne
  include HairballTwo
end
"))

(defslide thanks {:slide/layout :layout/blue}
  [:h1.title (casper/colorize "Thanks!")])

(defn slides
  "Add here all slides you want to see in your presentation."
  []
  (casper/render-slides
   [
    title
    brightin/plug
    brightin/brightmotive-intro
    composition
    hairball
    composition-in-music
    thanks
    ]
   {:layout/default layouts/logo-light
    :layout/dark    layouts/logo-dark
    :layout/blue    layouts/logo-blue}))
