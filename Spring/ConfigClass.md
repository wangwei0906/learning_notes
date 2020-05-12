# 二、通过Java代码装配Bean

首先我们创建一个配置类，代码如下：

```
package soundsystem;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration          
public class CDPlayerConfig {

}
```

**@Configuration主键表明这个类是一个配置类，该类应该包含在Spring应用上下文中如何创建bean的细节。**

要在JavaConfig中声明bean,我们需要在配置类中编写一个方法，这个方法中会创建所需类型的实例，我们需要给这个方法加@Bean注解，例如，我们声明一个CompactDisc中声明一个bean,代码如下：

```
package soundsystem;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration          
public class CDPlayerConfig {
    @Bean
    public CompactDisc sgtPeppers(){
        return new SgtPeppers();
    }

}
```

@Bean注解会告诉Spring这个方法将返回一个对象，该对象要注册为Spring应用上下文中的bean。这里我们没有为@Bean的任何属性赋值，也就是说这个Bean的ID也是默认的。这种方式默认情况下，bean的ID默认就是方法名。我们也可以为bean指定一个名字，代码如下：

```
@Bean(name="sgtpeppers")                         //这样我们为这个bean指定了ID为sgtpeppers
    public CompactDisc sgtPeppers(){
        return new SgtPeppers();
    }
```

## 借助JavaConfig实现注入

```
package soundsystem;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration          
public class CDPlayerConfig {

    @Bean(name="sgtpeppers")
    public CompactDisc sgtPeppers(){
        return new SgtPeppers();
    }

    public CDPlayer cdplayer(){
        return new CDPlayer(sgtPeppers());
    }

}
```

直接给出返回bean的方法名，就可以去注入依赖类，这里看上去像是方法调用，实际上并不是，默认情况下Spring中的bean都是单例的，我们没有必要去为第二个CDPlayer创建第一个完全相同的SgtPeppers实例,Spring会拦截对sgtPepper\(\)的调用，确保返回的是Spring所创建的bean。也就是Spring本身在调用sgtPeppers\(\)时创建的bean。

