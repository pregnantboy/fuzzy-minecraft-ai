//
//  RuleInput.h
//  FuzzyAIBuilder
//
//  Created by Ian Chen on 26/1/16.
//  Copyright © 2016 Ian Chen. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "RuleCondition.h"

@interface RuleInput : NSObject


- (instancetype)init;
- (instancetype)initForFirstInput;
- (instancetype)initWithOperator:(NSString *)op cond:(NSString *)condCode equality:(NSString *)isnot value:(NSString *)val;
- (void)setCondition:(NSString *)condCode;
- (NSString *)condition;
- (NSString *)operator;
- (void)setOperator:(NSString *)op;
- (void)setCondValue: (NSString *)value;
- (NSString *)equality;
- (NSString *)condValue;
- (void)setEquality:(NSString *)eq;
- (BOOL)
isSet;
@end
