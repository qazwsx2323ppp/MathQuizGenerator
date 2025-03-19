# 结对项目
#### 陈天鹏_3213004220 杜家楷_3123004223
------

#### 附录

| 这个作业属于哪个课程   | [软工1班](https://edu.cnblogs.com/campus/gdgy/SoftwareEngineeringClassof2023/) |
| ---------------------- | ------------------------------------------------------------ |
| **这个作业要求在哪里** | [结对项目](https://edu.cnblogs.com/campus/gdgy/SoftwareEngineeringClassof2023/homework/13326) |
| **这个作业的目标**     | 尝试进行结对项目，了解合作进行项目的流程                     |



### 仓库网址：[qazwsx2323ppp/MathQuizGenerator](https://github.com/qazwsx2323ppp/MathQuizGenerator)



## PSP图

| PSP2.1                                | Personal Software Process Stages      | 预估耗时（分钟） | 实际耗时（分钟） |
| ------------------------------------- | ------------------------------------- | ---------------- | ---------------- |
| Planning                              | 计划                                  | 30               | 50               |
| Estimate                              | 估计这个任务需要多少时间              | 10               | 10               |
| Development                           | 开发                                  | 200              | 350              |
| Analysis                              | 需求分析 (包括学习新技术)             | 300              | 500              |
| Design Spec                           | 生成设计文档                          | 30               | 30               |
| Design Review                         | 设计复审                              | 40               | 30               |
| Coding Standard                       | 代码规范 (为目前的开发制定合适的规范) | 30               | 35               |
| Design                                | 具体设计                              | 60               | 40               |
| Coding                                | 具体编码                              | 180              | 180              |
| Code Review                           | 代码复审                              | 60               | 60               |
| Test                                  | 测试（自我测试，修改代码，提交修改）  | 300              | 600              |
| Reporting                             | 报告                                  | 60               | 100              |
| Test Report                           | 测试报告                              | 40               | 50               |
| Size Measurement                      | 计算工作量                            | 40               | 60               |
| Postmortem & Process Improvement Plan | 事后总结, 并提出过程改进计划          | 50               | 60               |
|                                       | 合计                                  | 990              | 1200             |



## 效能分析

 #### 热点分析

![](https://img2024.cnblogs.com/blog/3374254/202503/3374254-20250316201051774-699402776.png)

 分析显示，大部分CPU时间消耗在分数相关的逻辑处理上，主要包括：

 - gcd：计算最大公约数以简化分数
 - parseFractoation：分数解析

 鉴于分数操作是系统的核心功能，且其计算复杂度本身高于整数和小数，这部分开销是合理的。

 #### 火焰图

 ![](https://img2024.cnblogs.com/blog/3374254/202503/3374254-20250316204657629-374140393.png)

 火焰图进一步证实了分数处理逻辑占用了主要计算资源。然而，从更宏观的角度看，大量时间用于表达式正确性校验。虽然确保表达式正确是必要需求，但可以考虑在算法设计层面进行优化，通过构造性方法确保生成的表达式天然合规，从而避免生成后再解析验证的额外开销。



## 设计实现过程

### 主要类

>* 主函数入口：Main.java
>* 定义结构：
>  * Expression.java //定义表达式树结构
>  * Operator.java //运算符枚举，优先级定义
>* 功能类：
>  * ExpressionGenerator.java //递归生成表达式树
>  * Operator.java // 控制生成表达式树符号节点
>  * NumberNode.java //控制生成表达式树数字节点
>  * PostfixConverter.java //将表达式生成逆波兰表达式
>  * PostfixCalculator.java // 计算逆波兰表达式
>  * Fraction.java //真分数处理

#### 实现过程流程图

<img width="306" alt="微信图片_20250319161100" src="https://github.com/user-attachments/assets/7250fa13-5f00-424e-bab4-22cba52fb3ac" />

<img width="231" alt="微信图片_20250319161117" src="https://github.com/user-attachments/assets/24841a06-5a1a-4274-a1bb-2e580caa0fb4" />



## 代码说明

#### 表达式树生成主要代码

```java
 private ExpressionNode buildExpressionTree(int remainingOps, boolean bracketsUsed,
                                               boolean isRoot, boolean inBracketContext,
                                               int parentPriority, boolean alreadyInBrackets) {
        if (remainingOps <= 0) {
            return new NumberNode(Fraction.generate(range));
        }

        Operator op = randomOperator();
        int leftOps = random.nextInt(remainingOps);
        int rightOps = remainingOps - leftOps - 1;

        // 强化括号上下文传递，避免重复添加括号
        boolean canAddBracket = !isRoot && !inBracketContext && remainingOps >= 2 && !alreadyInBrackets;
        boolean addBrackets = canAddBracket && shouldAddBrackets(remainingOps);

        // 生成子树时严格传递上下文状态
        ExpressionNode left = buildSubTree(leftOps, bracketsUsed || addBrackets,
                addBrackets, op.getPriority(), addBrackets);
        ExpressionNode right = buildSubTree(rightOps, bracketsUsed || addBrackets,
                addBrackets, op.getPriority(), addBrackets);

        return new OperatorNode(op, left, right, addBrackets, isRoot, parentPriority);
    }

    private ExpressionNode buildSubTree(int remainingOps, boolean bracketsUsed,
                                        boolean inBracketContext, int parentPriority, boolean alreadyInBrackets) {
        if (remainingOps > 0 && random.nextDouble() < 0.4) {
            return buildExpressionTree(remainingOps, bracketsUsed, false,
                    inBracketContext, parentPriority, alreadyInBrackets);
        }
        return new NumberNode(Fraction.generate(range));
    }
```

#### 生成逆波兰表达式主要代码

```java
public class PostfixConverter {
    public static List<String> convert(List<String> infix) {
        List<String> output = new ArrayList<>();
        Stack<String> stack = new Stack<>();

        for (String token : infix) {
            if (token.equals("(")) {
                stack.push(token);
            } else if (token.equals(")")) {
                while (!stack.peek().equals("(")) {
                    output.add(stack.pop());
                }
                stack.pop();
            } else if (isOperator(token)) {
                Operator currentOp = Operator.fromSymbol(token);
                while (!stack.isEmpty() && !stack.peek().equals("(") &&
                        comparePriority(stack.peek(), currentOp) >= 0) {
                    output.add(stack.pop());
                }
                stack.push(token);
            } else {
                output.add(token);
            }
        }

        while (!stack.isEmpty()) {
            output.add(stack.pop());
        }
        return output;
    }
```



 ## 单元测试

 ![](https://img2024.cnblogs.com/blog/3374254/202503/3374254-20250316215213633-1472672741.png)
 整体覆盖率80%，关键路径覆盖率100%


 #### 测试设计概览

 本项目的测试套件主要针对以下组件进行了测试：

  1. **ExpressionGeneratorTest** - 测试表达式生成功能
  2. **PostfixConverterTest** - 测试中缀表达式到后缀表达式的转换
  3. **PostfixCalculatorTest** - 测试后缀表达式的计算功能（待开发）
  4. **MainTest** - 测试主程序的功能（待开发）

 #### 测试组件详情

 ##### ExpressionGeneratorTest

 该测试组件验证表达式生成器的功能正确性，主要包括：

 - **testGenerateExpression**: 测试表达式生成器能够根据指定的操作符数量（1-3个）生成有效的表达式。
   - 验证生成的表达式对象不为空
   - 验证表达式字符串格式化正确
   - 对1到3个操作符的所有情况进行测试

 - **testRangeConstraint**: 测试表达式生成器遵循指定的数值范围约束。
   - 设置数值范围为5，生成多个表达式确保不会崩溃
   - 目前仅验证基本功能，后续可优化为具体验证数值范围

 ##### PostfixConverterTest

 该测试组件验证中缀表达式到后缀表达式转换的正确性：

 - **testSimpleInfixToPostfixConversion**: 测试简单表达式的转换。
   - 将"2 + 3"中缀表达式正确转换为"2 3 +"后缀表达式

 - **testComplexInfixToPostfixConversion**: 测试包含括号的复杂表达式转换。
   - 将"(2 + 3) * 4"中缀表达式正确转换为"2 3 + 4 *"后缀表达式

 #### 待开发测试

 ##### PostfixCalculatorTest (待实现)

 计划测试后缀表达式计算器，包括：

 - 基本四则运算测试
 - 复杂表达式计算测试
 - 边界情况处理测试

 #### MainTest (待实现)

 计划测试主程序功能，包括：

 - 命令行参数解析测试
 - 文件生成测试
 - 集成测试验证整体流程

 #### 运行测试

 所有测试均使用Maven自动化构建和测试，可通过以下命令运行：

 ```bash
mvn test
 ```

 或使用IDE（如IntelliJ IDEA、Eclipse）中的测试运行功能直接运行单个测试类或方法。



## 项目小结

#### 项目概述

本次项目通过结对编程的方式完成，目标是实现一个简单的表达式解析器或计算器。经过共同讨论分析，我们利用表达式树结构，逆波兰表达式等方法完成了项目

#### 代码分工

##### 分工方式

- 陈天鹏负责代码逻辑实现。
- 杜家楷负责测试程序，完善代码结构。

#### 运行错误的讨论与解决

##### 讨论与协作

- 遇到问题时，通过讨论明确问题根源，分工解决。

#### GitHub 代码管理

##### 分支管理

- 为每个功能模块创建独立的分支。
- 完成功能模块后，通过 Pull Request（PR）将代码合并到主分支（`main`）。

##### 代码审查

- 在每次提交 PR 后，进行代码审查，确保代码的可读性和可维护性。

##### 协作工具

- 使用 GitHub 的评论功能在代码审查时提出修改建议。
- 使用 GitHub 的合并工具解决代码冲突。

#### 总结与收获

通过本次结对项目，我们不仅完成了预期的功能实现，还积累了以下经验：

- **代码分工与协作**：明确分工提高效率，但需要通过频繁沟通确保代码一致性。
- **运行错误的解决**：遇到错误时，及时讨论和协作能够快速定位并解决问题。
- **代码质量管理**：通过代码审查和版本控制工具，能够有效提高代码质量和可维护性。

#### 未来改进方向

1. **代码优化**：尝试使用更简洁的代码实现相同功能，例如通过泛型或 lambda 表达式简化逻辑。
2. **自动化测试**：引入自动化测试工具（如 JUnit），提高测试效率。

通过本次结对项目，我们不仅完成了代码实现，还积累了宝贵的协作经验，为未来的开发打下了坚实的基础。

