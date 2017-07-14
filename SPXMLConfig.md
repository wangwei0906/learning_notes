---
# 二、通过XML装配bean

使用xml方式时，首先我们要新建一个XML文件，如下：

```
<?xml version="1.0" encoding="UTF-8"?>

<beans xmlns="http://www.springframework.org/schema/beans"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xmlns:context="http://www.springframework.org/schema/context"
  xsi:schemaLocation="http://www.springframework.org/schema/beans 
       http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context>

</beans>
```
然后我们声明一个简单的<bean>,如下：

```
<bean class="soundsystem.Sgtpeppers"/>
```

通过class来指定，并且使用类的全限定名。上面中没有给出bean的ID,默认情况下，bean的ID就是class的全限定+#编号，所以这个例子，bean的ID就是soundsystem.Sgtpeppers#0。这里#0表示编号，若有多个相同类型的bean,自动增加编号用以区别。我们为了减少繁琐，一般给需要用到的bean指定一个ID,例如在XML中如果想引用这个bean,就需要给出全限定名这样的ID过于雍长。指定一个ID就可以较少繁杂了。

## 借助构造器注入初始化bean

我们借助构造器注入初始化bean，有两种基本的配置方案可供选择：

- [x] 1. <constructor-arg>元素
 
- [x] 2.使用Spring3.0所引用入的C-命名空间

构造器注入bean引用，在xml文件中加如下内容：


```
<bean id="cdPlayer" class="soundsystem.CDPlayer">
    <constructor-arg ref="compactDisc"/>
</bean>
```
我们也可以使用Spring的c-命名空间的方式，c-命名空间是在Spring3.0中引入的，它是在XML中更为简洁的描述构造器参数的方式。要使用它必须在XML的头部声明其模式。声明如下：


```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
 xmlns:c="http://www.springframework.org/schema/c"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xmlns:context="http://www.springframework.org/schema/context"
  xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd


</beans>
```
声明完c-命名空间后，我们就可以用它来声明构造器参数，如下所示:

```
<bean id="cdPlayer" class="soundsystem.CDPlayer" c:cd-ref="compactDisc"/>

```
> 上面中，属性名以c开头，也就是命名空间的前缀，cd表示对CDPlayer中的字段名为cd的属性注入，-ref就是引用引用对象。
> 这样和上面<constructor-arg ref="compactDisc"/>这用形式效果相同，这样同样有一个弊端，那就是我们必须知道这个类中构造的参数的引用变量名，这样可以用索引识别的方式来解决这个问题，所谓索引识别就是给出构造器的参数的索引而不再给参数的名称，所以上面有这样一种替换方案。


```
<bean id="cdPlayer" class="soundsystem.CDPlayer" c:_0-ref="compactDisc"/>
```

表示为参数列表总索引为0的引用变量注入一个CompactDisc的bean,如果只有一个构造器参数，还有另一种方案，如下：

```
<bean id="cdPlayer" class="soundsystem.CDPlayer" c:-ref="compactDisc"/>
```
同上面两种方式相同。

---


## 将字面值注入到构造器中

假设我们创建一个新的CompactDisc的实现类，如下所示：


```
package soundsystem;

public class BlankDisc implements CompactDisc {
    
    private String title;
    private String artist;
    
    public BlankDisc(String title, String artist) {
        super();
        this.title = title;
        this.artist = artist;
    }
    
    public void play() {
        System.out.println("播放"+artist+"的"+title);
    }

}
```
通过构造器我们可以为这个BlankDisc中的title和artist两个字段初始化，传什么值就给赋什么值避免硬编码，如果要在XML将字面值注入到这个类的构造器中，则使用<constructor-args>这种方式配置如下：


```
<bean id="compactDisc" class="soundsystem.BlankDisc"> 
    <constructor-arg value="雨后虹"/>
    <constructor-arg value="郭燕妮"/>
</bean>
```
如果我们使用c-命名空间，则有两种方式，分别如下：


```
<bean id="compactDisc" class="soundsystem.BlankDisc" c:_title="雨后虹" c:_artist="郭燕妮"/>
```
或者


```
<bean id="compactDisc" class="soundsystem.BlankDisc" c:_0="雨后虹" c:_1="郭燕妮"/>
```

## 装配集合

实际中很多时候会用到集合，如何装配集合呢？来个例子，我们将上面定义的BlankDisc这个类中增加一个集合字段tracks(音轨),修改后代码如下：


```
import java.util.List;

public class BlankDisc implements CompactDisc {
    
    private String title;
    private String artist;
    private List<String> tracks;
    
    public BlankDisc(String title, String artist,List<String> tracks) {
        super();
        this.title = title;
        this.artist = artist;
        this.tracks=tracks;
    }
    
    public void play() {
        System.out.println("播放"+artist+"的"+title);
        for(String track:tracks){
            System.out.println("-Track:"+track);
        }
    }

}
```
现在我们来装配这个List，利用list元素，代码如下：


```
<bean id="compactDisc" class="soundsystem.BlankDisc"> 
        <constructor-arg value="雨后虹"/>
        <constructor-arg value="郭燕妮"/>
        <constructor-arg>
        <list>
                <value>Music_one</value>
                <value>Music_two</value>
                <value>Music_three</value>
                <value>Music_four</value>
                <value>Music_five</value>
                <value>Music_six</value>
                <value>Music_seven</value>
        <list>
        </constructor-arg>
</bean>
```

与之类似，我们可以使用<ref>取代<value>,实现bean引用列表装配，代码如下：

```
<bean id="compactDisc" class="soundsystem.BlankDisc"> 
    <constructor-arg value="雨后虹"/>
    <constructor-arg value="郭燕妮"/>
    <constructor-arg>
        <list>
            <ref>Music_one</ref>
            <ref>Music_two</ref>
            <ref>Music_three</ref>
            <ref>Music_four</ref>
            <ref>Music_five</ref>
            <ref>Music_six</ref>
            <ref>Music_seven</ref>
        </list>
    </constructor-arg>
</bean>
```
既然可以用<list>来装配List集合，那也可以用<set>来转配Set集合，如下：

```
<bean id="compactDisc" class="soundsystem.BlankDisc"> 
    <constructor-arg value="雨后虹"/>
    <constructor-arg value="郭燕妮"/>
    <constructor-arg>
        <set>
            <ref>Music_one</ref>
            <ref>Music_two</ref>
            <ref>Music_three</ref>
            <ref>Music_four</ref>
            <ref>Music_five</ref>
            <ref>Music_six</ref>
            <ref>Music_seven</ref>
        </set>
    </constructor-arg>
</bean>
```

其余的类推。

注意：使用c-命名空间无法完成对集合的装配

---


## 设置属性

上面都是用构造器注入的，下面尝试使用Setter方法，学一下使用Spring XML实现属性注入，假设属性注入的CDPlayer如下所示：


```
package soundsystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component                               //将CDPlayer也声明为一个组件
public class CDPlayer {
    private CompactDisc cd;
    
    @Autowired                      //注入一个CompactDisc的bean
    public void  setCDPlayer(CompactDisc cd){
        this.cd=cd;
    }
    public void play(){
        cd.play();
    }
}
```
这样我们无法通过构造器来设置属性了，我们可以用Setter方法配置，XML中主要内容如下：

```
<bean id="cdPlayer" class="soundsystem.CDPlayer">
    <property name="compactDisc" ref="compactDisc"/>
</bean>
```
我们还可以使用p-命名空间来装配，首先我们要在XML头部引入p-命名空间，如下：

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
 xmlns:p="http://www.springframework.org/schema/p"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xmlns:context="http://www.springframework.org/schema/context"
  xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd

</beans>
```
按照以下方式装配compactDisc：


```
<bean id="cdPlayer" class="soundsystem.CDPlayer" p:compactDisc-ref="compactDisc"/>
```
这里和上面的c-命名空间，类似类推就是了。p-命名空间也无法来装配集合，但是可以利用Spring util-命名空间来做这项工作，首先引入util-命名空间，如下：

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
 xmlns:p="http://www.springframework.org/schema/p"
 xmlns:util="http://www.springframework.org/schema/util"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xmlns:context="http://www.springframework.org/schema/context"
  xsi:schemaLocation="http://www.springframework.org/schema/beans   
  http://www.springframework.org/schema/beans/spring-beans.xsd
  http://www.springframework.org/schema/util
  http://www.springframework.org/util/spring-util.xsd"
</beans>
```
引入util-命名空间后，就可以借助<util:list>元素，它会创建一个列表的bean。如下


```
  <util:list id="trackList">
    <value>Track_one</value>
    <value>Track_two</value>
    <value>Track_three</value>
    <value>Track_four</value>
    <value>Track_five</value>
  </util:list>
  
  <bean id="compactDisc" 
  class="soundsystem.CompactDisc"
  p:title="雨后虹"
  p:artist="郭燕妮"
  p:tracks-ref="trackList"
 />
```

通过以上方式，可以利用util-命名空间先把集合对象声明成一个独立的bean然后再去引用它。

元素 | 描述
---|---
<util:constant> | 引用某个类型的public static域并将其暴露为bean
<util:list>|创建一个java.util.List类型的bean,其中包含值或引用
<util:map> | 创建一个java.util.Map类型的bean,其中包含值或引用
<util:properties> | 创建一个java.util.Properties类型的bean
<util:property-path> | 引用一个bean的属性（或内嵌属性），并将其暴露为bean
<util: set> |创建一个java.util.Set类型的bean,其中包含值或引用


---
 ## 导入和混合配置

当我们使用自动化发现机制来做装配工作是时，并不在意bean来自哪里，但是有些情况，可能用到显示的配置，XML与JavaConfig混合使用，就存在一些问题，比如JavaConfig中如何引用XML中的bean呢？XML如何应用JavaConfig中的bean呢？
### 在JavaConfig中引入XML配置
先学习下两个不同的JavaConfig的混合配置吧，如下：

```
package soundsystem;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration          
public class CDConfig {
    
    @Bean(name="compactDisc")
    public CompactDisc compactDisc(){
        return new SgtPeppers();
    }
    
}
```
定义一个CDconfig配置类,我们在定义一个CDPlayerConfig配置类，在CDPlayerConfig中引用CDconfig的compactDisc这个bean,如下：


```
package soundsystem;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(CDConfig.class)
public class CDPlayerConfig {
    @Bean
    public CDPlayer cdPlayer(){
        return new CDPlayer(compactDisc);
    }
}
```
利用@Import这个注解将CDConfig这配置类给包含进来就可以实现两个JavaConfig配置类的混合配置，然后还有一种更好的方法，可以不用@Import这个注解，可以创建一个更高级别的配置类，然后用@Import分别将另外的两个配置类给包含进来。如下：


```
package soundsystem;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({CDConfig.class,CDPlayerConfig.class})
public class SoundSystemConfig {

}
```

下面看看如何在JavaConfig中引入XML中的bean。
我们现在将上面的CDConfig，改成XML配置形式的，为这个XML文件命名叫cd_config.xml配置内容如下：


```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xmlns:context="http://www.springframework.org/schema/context"
  xsi:schemaLocation="http://www.springframework.org/schema/beans   
  http://www.springframework.org/schema/beans/spring-beans.xsd

    <bean id="compactDisc" class="soundsystem.SgtPeppers"/>
    
</beans>
```

现在我们将上面那个更高级别的配置类SoundSystemConfig.xml中改造一下，演示一下同时引入一个JavaConfig和一个XML文件来完成混合配置，如下：


```
package soundsystem;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

@Configuration
@Import(CDPlayerConfig.class)
@ImportResource("classpath:cd_config.xml")
public class SoundSystemConfig {

}
```

---

### 在XML配置中引用JavaConfig

一样，先看看两个XML文件的导入和混合配置，cd的XML配置文件借用上cd_config.xml，现在我们在CDPlayerConfig的XML形式cdPlayer_config.xml中引用它，代码如下：


```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xmlns:c="http://www.springframework.org/schema/c"
  xsi:schemaLocation="http://www.springframework.org/schema/beans   
  http://www.springframework.org/schema/beans/spring-beans.xsd"
  
  <import resource="cd_config.xml"/>
  
  <bean id="cdPlayer" class="soundsystem.CDPlayer">
    <c:cd-ref="compactDisc" />
  </bean>
 
</beans>
```

---

再看看如何在XML中导入JavaConfig配置类来实现混合装配，其实很简单，我们已经很熟悉了能够在XML中配置一个Java类，那么我们可以直接把这个JavaConfig配置到XML文件中，这样就将JavaConfig配置导入到了XML中了，就可以在XML中装配JavaConfig中声明的bean了，如下：


```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xmlns:c="http://www.springframework.org/schema/c"
  xsi:schemaLocation="http://www.springframework.org/schema/beans   
  http://www.springframework.org/schema/beans/spring-beans.xsd"
  
  <bean class="soundsystem.CDConfig">
  
  <bean id="cdPlayer" class="soundsystem.CDPlayer">
    <c:cd-ref="compactDisc" />
  </bean>
 
</beans>
```
另一种更高级的方式，我们新建一个更高级别的XML文件，在其中导入一个XML配置文件和一个JavaConfig类实现混合配置，代码如下：

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xmlns:c="http://www.springframework.org/schema/c"
  xsi:schemaLocation="http://www.springframework.org/schema/beans   
  http://www.springframework.org/schema/beans/spring-beans.xsd"
  
  <bean class="soundsystem.CDConfig">
  <import resource="cdPlayer_config.xml"
 
</beans>
```

终于写完了，在大学室友的推荐下第一次使用MarkDown文件做笔记，太喜欢这种笔记风格了，开心。
