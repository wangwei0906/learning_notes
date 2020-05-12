# 一、自动化装配bean

Spring使用两个角度来实现自动化装配：


- 组件扫描（Component scanning）:Spring会自动发现应用上下文中创建的bean
- 自动装配（auto wiring）:Spring自动满足bean之间的依赖。
    
我们以一个例子来作为驱动，CD播放器依赖CD才能完成它的使命，我们就以这个例子开始，对CDPlayer这个bean中自动化装配一个CompactDisc的bean,我们先定义一个CompactDisc的接口如下：


```
package soundsystem;

public interface CompactDisc {
    public void play();        
}
```

通过接口定义了CD播放器能够对CD进行的操作，我们再来定义一个SgtPeppers类

```
package soundsystem;
import org.springframework.stereotype.Component;

@Component                      //@Component这注解表名将该类作为一个组件类
public class SgtPeppers implements CompactDisc{       //实现CompactDisc接口

    private String title ="雨后虹";
    private String artist="郭燕妮";
    public void play() {
        System.out.println("播放"+artist+"的"+title);
    }
    
}
```
> 在类上面我们使用了==@Component这个注解，这个注解表明将该类作为组件类，并告知Spring要为这个类创建bean，这样我们就不必显式配置SegPeppers bean了，因为这个类使用了@Component注解，所以Spring会为你把事情处理妥当。---

不过Spring的组件扫描默认是不启动的，所以我们还需要显式的配置一下Spring从而命令它去寻找所有带有@Component的注解的类，并为其创建bean。

我们要如何的显示配置去启动Spring的组件扫描呢？下面看看启动组件扫描的两种方式

## Spring启动组件扫描的两种方式：

**1、通过配置类来启动组件扫描**

我们可以定义一个配置类，然后在类上使用@ComponentScan这个注解启动组件扫描。代码如下：

package soundsystem;


```
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration          //使用@Configuration来表明这是一个配置类
@ComponentScan          //启动组件扫描
public class CDPlayerConfig {
    
}
```
**2、通过XML启用组件扫描**

使用Spring context命名空间的<context:component-scan>元素，XML配置如下：


```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xmlns:context="http://www.springframework.org/schema/context"
  xmlns:c="http://www.springframework.org/schema/c"
  xmlns:p="http://www.springframework.org/schema/p"
  xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">

  <context:component-scan base-package="soundsystem" />   <!--xml方式的启用组件扫描

</beans>
```

我们采用JavaConfig方式测试一下组件扫描能够发现CompactDisc，测试代码如下：


```
package soundsystem;

import static org.junit.Assert.*;         

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=CDPlayerConfig.class)   //加载配置类
public class CDPlayerTest {
    @Autowired
    private CompactDisc cd;
    
    @Test
    public void cdShouldNotBeNull(){
        assertNotNull(cd);
    }
    
}
```
测试成功。说明，已经成功的将一个CompactDisc的bean了。

我们知道自动发现机制和自动装配，主要使用组件扫描和自动装配来实现。下面学习一下@ComponentScan和@Componet两个注解。


## 为组件扫描的bean命名

第一种方式，**设置@Component中的values属性**，代码如下：

```
@Component("sgtpeppers")                     
public class SgtPeppers implements CompactDisc{       
    .......
}
```

这样我们就为SgtPeppers的bean设置了Id为sgtpeppers。我们要是知道。我们在使用@Component时没有设置value属性为其设置ID，但是他是有默认值的，它的默认会根据类名为其指定一个ID，就是将类名的大写字母变成小写，即sgtPeppers

第二种方式，**使用@Named注解**，代码如下：

```
@Named("sgtpeppers")                     
public class SgtPeppers implements CompactDisc{       
    .......
}
```
## 设置组件扫描的基础包

我们没有为@ComponentScan这个注解设置任何属性，这就意味着，按照默认规则，以配置类的所在的包作为基础包来扫描组件，设置组件扫描的基础包的方法如下：


```
@Comfiguration
@ComponentScan("soundsystem")
public class CDPlayerConfig(){
}
```
上面代码，设置了扫描的基础包是soundsystem，还可以

```
@Comfiguration
@ComponentScan(basePackages="soundsystem")
public class CDPlayerConfig(){
}
```
这个与上面实现的效果一样，不过我们看到basePackages使用的复数形式，这表明可以用这个属性设置多个基础包，我们将basePackages属性设置为一个数组即可，代码如下：

```
@Comfiguration
@ComponentScan(basePackages={"soundsystem"，"video"})
public class CDPlayerConfig(){
}
```
上面就可以设置扫描多个基础包了，但是上面这些方法也不是类型安全的，Spring还提供了一种方法。
```
@Comfiguration
@ComponentScan(basePackageClasses={CDPlayer.class,DVDPlayer.class})
public class CDPlayerConfig(){
}
```
这种方式，以为这告诉Spring将上面指定的类所在的包作为基础包。

## 通过为bean添加注解实现自动装配
  继续前面的例子，我们可以将一个CompactDisc的bean注入到一个CDPlayer类中，代码如下：

```
package soundsystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component                               //将CDPlayer也声明为一个组件
public class CDPlayer {
    private CompactDisc cd;
    
    @Autowired                      //注入一个CompactDisc的bean
    public CDPlayer(CompactDisc cd){
        this.cd=cd;
    }
    public void play(){
        cd.play();
    }
}
```
这样我们就将一个CompactDisc的bean注入到CDPlayer类中，@Autowired这个注解，不仅可以用在构造器上，还可以Setter方法上，在字段上。不管是构造器，Setter方法还是其他方法，Spring都会尝试满足方法，字段上所声明的依赖，假如有且只有一个bean匹配依赖需求的话，那么这个bean就会被装配进来。如果没有匹配到bean，那么在应用上下文创建的时候，Spring会抛出一个异常，你可以将@Autowired的required属性设置为false,如下：

```
@Autowired(required=false)                 //设置@Autowired的required属性值为false
    public CDPlayer(CompactDisc cd){
        this.cd=cd;
    }
```

设置@Autowired的required属性值为false后，Spring在没有匹配到bean后，会让cd这个bean处于未装配状态，这样我们就应该在方法体内做NUll检查了。我们来验证一下自动装配吧。
验证代码如下：


```
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=CDPlayerConfig.class)   //加载配置类
public class CDPlayerTest {
    
    @Autowired
    private CDPlayer player;
    
    @Autowired
    private CompactDisc cd;
    
    @Test
    public void cdShouldNotBeNull(){
        System.out.println(cd);
        assertNotNull(cd);
    }
    
    @Test
    public void play(){
        player.play();
    }
}
```

测试通过。自动装配成功！！！