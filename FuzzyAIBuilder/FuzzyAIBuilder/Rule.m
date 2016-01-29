//
//  Rule.m
//  FuzzyAIBuilder
//
//  Created by Ian Chen on 26/1/16.
//  Copyright © 2016 Ian Chen. All rights reserved.
//

#import "Rule.h"
#define inputsKey @"inputsKey"
#define actionKey @"actionKey"
#define modifierKey @"modifierKey"

@interface Rule() {
    NSMutableArray *inputs;
    NSString *action;
    NSString *modifier;
}
@end

@implementation Rule


- (instancetype) init {
    self = [super init];
    if (self) {
        inputs = [[NSMutableArray alloc] init];
        [inputs addObject:[[RuleInput alloc] initForFirstInput]];
    }
    return self;
}

- (RuleInput *)getRuleInputAtIndex:(NSInteger) index {
    if (index < [inputs count]) {
        return [inputs objectAtIndex:index];
    } else {
        NSLog(@"Rule Input out of range");
        return nil;
    }
}

- (void)createNewRuleInput {
    RuleInput* newRuleInput = [[RuleInput alloc] init];
    [inputs addObject: newRuleInput];
}

- (void)deleteRuleInput:(NSInteger)index {
    [inputs removeObjectAtIndex:index];
    if (index == 0) {
        [self setOperator:@"IF" atIndex:0];
    }
}


- (void)setOperator:(NSString *)operator atIndex:(NSInteger)index {
    [(RuleInput *)[inputs objectAtIndex:index] setOperator:operator];
}

- (void)setEquality:(NSString *)equality atIndex:(NSInteger)index {
    [(RuleInput *)[inputs objectAtIndex:index] setEquality:equality];
}

- (void)setCond:(NSString *)cond atIndex:(NSInteger) index {
    RuleInput *input = [self getRuleInputAtIndex:index];
    [input setCondition:cond];
}

- (void)setValue:(NSString *)value atIndex:(NSInteger) index {
    RuleInput *input = [self getRuleInputAtIndex:index];
    [input setCondValue:value];
}

- (void)setAction:(NSString *)act {
    action =act;
}

- (void)setModifier:(NSString *)mod {
    modifier = mod;
}

- (NSString *)getCondAtIndex:(NSInteger)index {
    RuleInput *input = [self getRuleInputAtIndex:index];
    return [input condition];
}

- (NSString *)getValueAtIndex:(NSInteger)index {
    RuleInput *input = [self getRuleInputAtIndex:index];
    return [input condValue];
}

- (NSString *)getOperatorAtIndex:(NSInteger)index {
    RuleInput *input = [self getRuleInputAtIndex:index];
    return [input operator];
}

- (NSString *)getEqualityAtIndex:(NSInteger)index {
    RuleInput *input = [self getRuleInputAtIndex:index];
    return [input equality];
}

- (NSString *)getAction {
    return action;
}

- (NSString *)getModifier  {
    return modifier;
}

- (NSArray *)getPossibleValuesAtIndex:(NSInteger) index {
    if (index >= [inputs count]) {
        return nil;
    }
    RuleInput *input = [self getRuleInputAtIndex:index];
    return [(RuleCondition *)[input condition] getPossibleValues];
}

- (NSArray *)getPossibleModifiers {
    if (action == nil) {
        return nil;
    }
    return [RuleOutput getPossibleModifiersForAction:action];
}

- (NSInteger)getNumInputs {
    return [inputs count];
}

- (NSString *)getRuleString {
    if (![self isComplete]) {
        return @"Incomplete Rule";
    }
    NSMutableString *resultString = [[NSMutableString alloc]init];
    for (RuleInput *input in inputs) {
        if ([input isKindOfClass:[RuleInput class]] &&[input isSet]) {
            [resultString appendString:[input getRuleInputString]];
        }
    }
    [resultString appendString:[NSString stringWithFormat:@"THEN %@ %@.", action, modifier]];
    return resultString;
}

- (BOOL)isComplete {
    for (RuleInput *input in inputs) {
        if (![input isSet]) {
            return NO;
        }
    }
    if (modifier == nil) {
        return NO;
    }
    return YES;
}

#pragma mark - NSCoding 
- (instancetype)initWithCoder:(NSCoder *)aDecoder {
    self = [super init];
    if (self) {
        inputs = [[aDecoder decodeObjectForKey:inputsKey] mutableCopy];
        action = [aDecoder decodeObjectForKey:actionKey];
        modifier = [aDecoder decodeObjectForKey:modifierKey];
    }
    return self;
}

- (void)encodeWithCoder:(NSCoder *)aCoder {
    [aCoder encodeObject:inputs forKey:inputsKey];
    [aCoder encodeObject:action forKey:actionKey];
    [aCoder encodeObject:modifier forKey:modifierKey];
}

@end
