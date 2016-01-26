//
//  RuleInput.m
//  FuzzyAIBuilder
//
//  Created by Ian Chen on 26/1/16.
//  Copyright © 2016 Ian Chen. All rights reserved.
//

#import "RuleInput.h"

@interface RuleInput () {
    NSString *operator;
    NSString *equality;
    RuleCondition *cond;
}
@end

@implementation RuleInput
- (instancetype)init {
    self = [super init];
    if (self) {
        cond = [[RuleCondition alloc] init];
        operator = @"AND";
        equality = @"IS";
    }
    return self;
}

- (instancetype)initForFirstInput {
    self = [super init];
    if (self) {
        cond = [[RuleCondition alloc] init];
        operator = @"IF";
        equality = @"IS";
    }
    return self;
}

- (instancetype)initWithOperator:(NSString *)op cond:(NSString *)condCode equality:(NSString *)isnot value:(NSString *)val {
    self = [super init];
    if (self) {
        operator = op;
        cond = [[RuleCondition alloc] initWithCond:condCode value:val];
        equality = isnot;
    }
    return self;
}

- (void)setCondition:(NSString *)condCode {
    [cond setCondCode:condCode];
}

- (void)setCondValue: (NSString *)value {
    [cond setCondValue:value];
}

- (NSString *)condValue {
    return [cond value];
}

- (NSString *)condition {
    return [cond cond];
}

- (NSString *)operator {
    return operator;
}

- (void)setOperator:(NSString *)op {
    operator = op;
}

- (NSString *)equality {
    return equality;
}

- (void)setEquality:(NSString *)eq {
    equality = eq;
}

- (BOOL)isSet {
    return [cond isSet];
}

@end
