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
     [:code.p-4 {:class (str (name lang) " p-4 rounded-lg")}
      (str/trim (str code))]]))

(defn- video [src]
  [:video.absolute.inset-0.h-full.w-full
   {:controls true}
   [:source {:data-src src}]])

(defslide title {}
  [:h1.title
   (casper/colorize
     ["Composition" :blue-600] " over " ["Convention" :red-600])])

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Rails

(defslide used-to-be-rails {}
  [:h1.title
   (casper/colorize "We used to be a " ["Rails shop." :blue-500])])

(defslide should-we-still-use-it {}
  [:h1.title
   (casper/colorize "Should we still " ["use it?" :red-500])])

(defslide rails-initial-commit {:slide/layout :layout/none}
  [:div.flex.max-w-3xl.mx-auto
   [:div.my-auto.mr-32
    [:h1.title.-mt-12 "Rails new"]
    [:h2.text-lg.opacity-75 (casper/colorize "gem install " ["hairball" :red-500])]]
   [:img {:src "./img/rails_initial_commit.png"}]])

(defn project-lifecycle []
  (for [i (range 4)]
    (casper/make-slide {:slide/layout :layout/blue}
      [:<>
       [:img.mx-auto.w-96 {:src (str "./img/project_lifecycle_" i ".svg")}]
       [:aside.notes
        "Statistical value of joy is higher in Rails ..."]])))

(def rails
  [(casper/transition-group :none
     [used-to-be-rails
      should-we-still-use-it])
   (casper/transition-group :none
     (into
       [rails-initial-commit]
       (project-lifecycle)))])

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Composition

(defslide composition-title {:slide/background-image "./img/hands.jpeg"
                             :slide/layout :layout/dark}
  [:h1.title "Composition"])

(defslide etymology {}
  [:<>
   [:h1.title.text-blue-500 "Com • ponere"]
   [:div.mx-48.mt-4.px-12.font-bold.text-gray-900
    [:span "Near"]
    [:span.ml-8 "To Place"]]])

(defslide xy-graph {}
  [:<>
   [:h1.title.mb-6 "Composition is powerful"]
   [:img.mx-auto {:src "./img/x_y_graph.png"}]])

(defslide can-drink? {}
  [:<>
   [:h1.title.mb-4 "Composition is powerful"]
   (code {:class "w-96" :lang :lang/clojure} "
(def can-drink?
  (comp #(>= % 21) :user/age))
")])

(defslide accounts-report {}
  [:<>
   [:h1.title.mb-4 "Composition is powerful"]
   (code {:lang :lang/clojure
          :class "w-128"} "
(def accounts-report
  (comp account/summary user/all-accounts))

(accounts-report db)
")
   [:div
    (casper/enumeration {:class "w-96 text-center"}
      (casper/colorize ["def accounts-report" :orange-500] " is the "
        ["composition" :blue-500])
      (casper/colorize "of "
        ["account/summary"   :red-500] " and "
        ["user/all-accounts" :red-500])
      (casper/colorize
        "which are the " ["composable" :blue-500] " elements"))]
   [:aside.notes
    [:ul
     [:li "Notice how both functions don't know about eachother"]
     [:li "Notice that the `def` is the composition, and it is done "
      "via it's composable parts !!!! VERY IMPORTANT"]
     [:li "Don't think like 'how will they compose' but in 'can they compose'"]]]])

(def composition
  (casper/transition-group :none
    [composition-title etymology xy-graph can-drink? accounts-report]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Composition in music

(defslide musician {:slide/background-image "./img/guitar.jpg"
                    :slide/layout :layout/dark}
  [:h1.title (casper/colorize "I am also a " ["musician" :pink-600])])

(defslide asylum {:slide/layout :layout/none
                  :slide/class "bg-black text-white"}
  [video "./img/asylum_snippet.mp4"])

(def COMP_COLOR :blue-500)
(def ARR_COLOR :orange-500)

(defn- c-v-a-title []
  [:h1.title (casper/colorize ["Composer" COMP_COLOR] " vs "
                              ["Arranger" ARR_COLOR])])

(defslide composer-v-arranger {:slide/background-image "./img/music_sheet2.jpg"
                               :slide/layout :layout/dark}
  [:<>
   (c-v-a-title)
   (casper/enumeration {:class "text-sm w-64 text-center"}
     (casper/colorize ["Composer" COMP_COLOR] " generates musical ideas")
     (casper/colorize ["Arranger" ARR_COLOR]  " assembles ideas into product")
     (casper/colorize "The " ["music    industry" :red-700] " acknowledges this")
     (casper/colorize "The " ["software industry" :red-700] " doesn't"))
   [:aside.notes
    [:ul
     [:li "Why some songs are harder to arrange than others"]
     [:li
      "Success of music industry in terms of output!! We "
      "keep coming up with new songs."]
     [:li "Questions? No? We all understand composers?"]]]])
;; TODO real book slide

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
"}
            {:title ["Make music" :black]
             :code-str "
(def make-music! (comp arranger composer))

(make-music!) ;; => \"🎼 Let it be 🎵\"
"
             }]]
       ^{:key (first title)}
       [:div.flex.flex-wrap.items-center
        [:h1.title.min-w-48 (casper/colorize title)]
        (code {:lang :lang/clojure
               :class "flex-grow"}
          code-str)]))
   [:aside.notes
    [:ul
     [:li
      "Again, notice that the composition is a list of many composable parts"]
     [:li "It would be trivial to compose different composers with different arrangers"]]]])

(def composition-in-music
  [musician
   asylum
   (casper/transition-group :none
     [composer-v-arranger c-v-a-code-example])])

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Tower of composability

(defn- apples-oranges-title []
  (casper/colorize
   "Let's compare " ["apples" :green-500] " to " ["oranges" :orange-500]))

(defslide apples-and-oranges {:slide/layout :layout/dark}
  [:h1.title (apples-oranges-title)])

(defslide the-tower {}
  [:<>
   [:h1.title (apples-oranges-title)]
   (code {:lang :ascii
          :class "mt-6 w-56"} "
+---------+
| Macro's |
+-+-------+-+
  | Classes |
  +-+-------+---+
    | Functions |
    +--+--------+-+
       |   DATA   |
       +----------+
")
   [:aside.notes
    [:ul
     [:li "EDN!!! readers"]
     [:li "Clojure knows how to push side-effects to the edge"]
     [:li "We should push behavior more to the edges too"]]]])

(defslide its-a-trap {:slide/background-image "./img/its_a_trap.gif"
                      :slide/layout :layout/dark}
  [:<>
   (code {:lang :lang/ruby :class "px-24"} "
class MyHairball
  include HairballOne
  include HairballTwo
end

MyHairball.new.do_something
")
   [:aside.notes
    [:ul
     [:li "Rails tries to compose using meta programming"]]]])

(def tower-of-composability
  (casper/transition-group :none
    [apples-and-oranges the-tower its-a-trap]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Examples in the wild

(defn ring-code []
  (code :lang/clojure "
(defn handler [req]
  {:status 200 :body \"Hello world!\"})
"))

(defn compojure-code []
  (code :lang/clojure "
(defroutes app
  (context \"/admin\"
    (GET \"/users\" [] c.users/index)))
"))

(defn reitit-code []
  (code :lang/clojure "
(def routes
  [\"/api\"
   [\"/ping\" ::ping]
   [\"/orders/:id\" ::order]])
"))

(def TW "")

(defn tailwind-code []
  (code {:lang :lang/clojure :class "text-xs"}  "
(defn card []
  [:div
   [:div.bg-white.sm:max-w-full.max-w-md.rounded.overflow-hidden.shadow-lg
    [:div.text-center.p-6.border-b
     [:img.h-24.w-24.rounded-full.mx-auto
      {:alt \"Randy Robertson\",
       :src \"https://randomuser.me/api/portraits/men/22.jpg\"}]
     [:p.pt-2.text-lg.font-semibold \"Randy Robertson\"]
     [:p.text-sm.text-gray-600 \"randy.robertson@example.com\"]
     [:div.mt-5
      [:a.border.rounded-full.py-2.px-4.text-xs.font-semibold.text-gray-700
       \"Manage your Google Account\"]]]]])
"))

(defn tailwind-card []
  [:div.w-64.mx-auto {:style {:transform "scale(0.7)"}}
   [:div.bg-white.sm:max-w-full.max-w-md.rounded.overflow-hidden.shadow-lg
    [:div.text-center.p-6.border-b
     [:img.h-24.w-24.rounded-full.mx-auto
      {:alt "Randy Robertson",
       :src "https://randomuser.me/api/portraits/men/22.jpg"}]
     [:p.pt-2.text-lg.font-semibold "Randy Robertson"]
     [:p.text-sm.text-gray-600 "randy.robertson@example.com"]
     [:div.mt-5
      [:a.border.rounded-full.py-2.px-4.text-xs.font-semibold.text-gray-700
       "Manage your Google Account"]]]]])

(def EXAMPLES
  [{:name "Ring"
    :composable? true
    :enumeration [[:span.italic "It's just data"]
                  (ring-code)]
    :notes ["So good nobody uses anything else"
            "Middleware stacks pretty well"]}
   {:name "Compojure"
    :enumeration [[:span.italic "Heavy use of macro's"]
                  (compojure-code)]
    :notes ["You can't merge routes"
            "You can't auto-generate this easily"]}
   {:name "Reitit"
    :composable? true
    :enumeration [[:span.italic "It's just data"]
                  (reitit-code)]
    :notes ["You can merge routes"
            "You can auto-generate these"]}
   {:name "Duct"          :composable? true}
   {:name "tailwind"
    :composable? true
    :enumeration [(tailwind-code)
                  [tailwind-card]]}
   {:name "specql"}
   {:name "Fulcro RAD"    :composable? true}
   {:name "Boyscout"      :composable? true}
   {:name "These slides!" :composable? true}])

(def examples
  (casper/transition-group :slide
    (for [{:keys [name composable? enumeration notes]} EXAMPLES]
      (let [[layout title-color] (if composable?
                                   [:layout/blue :white]
                                   [:layout/orange :orange-200])]
        (casper/make-slide {:slide/layout layout}
          [:<>
           [:h1.title (casper/colorize [name title-color])]
           (when enumeration
             (into [casper/enumeration {}] enumeration))
           [:div.absolute.left-0.bottom-0.p-16
            (if composable?
              (casper/colorize "👍" [" composable" title-color])
              (casper/colorize "👎" [" not" :white] [" composable" title-color]))]
           (casper/notes notes)])))))

(defslide duct-fender {:slide/layout :layout/none
                       :slide/class "bg-black text-white"}
  [:<>
   [video "./img/duct_fender_demo.mp4"]
   [:aside.notes
    "If the stars align and the elements compose, you can write "
    "Rails and Administrate in under a 100 lines of Clojure"]])

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Arrangement

(defslide thanks {:slide/layout :layout/dark
                  :slide/background-image "./img/hands_many.jpg"}
  [:h1.title (casper/colorize "Thanks!")])

(defn make-presentation
  "A rendered list of slides"
  []
  (casper/render-slides
    [title
     brightin/plug
     brightin/brightmotive-intro
     rails
     composition
     composition-in-music
     tower-of-composability
     examples
     duct-fender
     thanks]
    {:layout/default layouts/logo-light
     :layout/dark    layouts/logo-dark
     :layout/blue    layouts/logo-blue
     :layout/orange  layouts/logo-orange}))
