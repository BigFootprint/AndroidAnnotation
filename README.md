AndroidAnnotation
=================

为Android开发提供注解框架，解除Android中反复出现的findViewById等代码


##Project design
项目将为在Android Framework提供的Activity、Fragment、普通Class等之间添加一层注解组件，开发者继承这些组件，即可使用框架定义的注解进行项目开发。


##Project Todo
1. 为Activity提供注解
2. 为Fragment提供注解
3. 为普通class提供注解


##Project Doing
为Activity提供注解


##Attentation
- Activity都需要有一个属性叫做rootView，并且做出注解，框架会自动设置rootView作为setContentView()的参数；或者开发者可以不去设置该属性，而是在调用super.onCreate(savedInstanceState)之前调用setContentView()设置视图；设置了rootView就会以rootView为准；
- Fragment同上；
